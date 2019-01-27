package yellow7918.ajou.ac.janggi;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity3 extends AppCompatActivity {

    //API KEY
    //private static final String CLOUD_VISION_API_KEY = "AIzaSyAGDwXac6q3EBYEVdMmLEKrTV-mhhPYl-s";
    private static final String CLOUD_VISION_API_KEY = "AIzaSyAWe7xFMmM1m10S56xwmp_eSQ2jRX3rAJg";

    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = SignUpActivity3.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;

    //버튼들
    private Button btnDone;
    private Button btnCancel;

    //ID: Email
    private String email;
    private String userName;
    private String password;

    private String keyword;
    private boolean[] tag;

    //이미지, 이미지 처리중 및 처리완료를 출력 할 텍스트뷰
    static private ImageView img;

    static EditText comName;
    static EditText comNumber;
    static EditText comCEO;
    //EditText comAddr;

    private DatabaseReference mDatabase;

    private static ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        if (getIntent() != null) {
            email = getIntent().getStringExtra("email");
            userName = getIntent().getStringExtra("name");
            keyword = getIntent().getStringExtra("keyword");
            tag = getIntent().getBooleanArrayExtra("tag");
            password = getIntent().getStringExtra("password");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        init();
    }

    private void init() {

        if (getIntent() != null)
            email = getIntent().getStringExtra("email");

        img = findViewById(R.id.img);
        //imgDetail = findViewById(R.id.imgDetail);
        img.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       new AlertDialog.Builder(SignUpActivity3.this)
                                               .setTitle("복지카드 가져오기")
                                               .setPositiveButton("사진촬영", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       startCamera();
                                                   }
                                               })
                                               .setNegativeButton("앨범선택", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       startGalleryChooser();
                                                   }
                                               })
                                               .show();
                                   }
        });



        comName = findViewById(R.id.comName);
        comNumber = findViewById(R.id.comNumber);
        comCEO = findViewById(R.id.comCEO);
        // comAddr = activity.findViewById(R.id.comAddr);

        btnDone = findViewById(R.id.btnDone);
        btnCancel = findViewById(R.id.btnCancel);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (comName.getText().toString().length() == 0 || comNumber.getText().toString().length() == 0 || comCEO.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity3.this, "등록증을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = comName.getText().toString();
                String num = comNumber.getText().toString();
                String ceo = comCEO.getText().toString();
                // String addr = comAddr.getText().toString();

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("num", num);
                intent.putExtra("ceo", ceo);
                //intent.putExtra("addr",addr);
                List<Boolean> tags = new ArrayList<>();

                for (Boolean b : tag)
                    tags.add(b);

                User user = new User(email, userName, name, num, ceo, keyword, tags);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity3.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mDatabase.child("user").child(email.replace(".", "")).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(intent);

                                            finish();
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getBaseContext(), "회원 가입에 실패 했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity2.class);

                intent.putExtra("email", email);
                startActivity(intent);

                //finish();
            }
        });
    }

    //갤러리 접근
    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    //카메라 구동
    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {

            //카메라로 사진찍어서 가져오기
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    //이미지 띄우기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressBar.setVisibility(View.VISIBLE);
        img.setVisibility(View.GONE);
        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }

    public void uploadImage(Uri uri) {
        //이미지가 있을 때
        if (uri != null) {
            try {
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                MAX_DIMENSION);
                //클라우드 비전 실행
                callCloudVision(bitmap);
                //이미지뷰에 이미지 띄우기
                img.setImageBitmap(bitmap);

                //에러 예외처리
            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, "Image picking failed", Toast.LENGTH_LONG).show();
            }
            //이미지가 없을 때
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, "Image picker gave us a null image.", Toast.LENGTH_LONG).show();
        }
    }

    private Vision.Images.Annotate prepareAnnotationRequest(Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {

                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            //text detection. TEXT_DETECTION -> DOCUMENT_TEXT_DETECTION!
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature textDetection = new Feature();
                textDetection.setType("DOCUMENT_TEXT_DETECTION");
                textDetection.setMaxResults(10);
                add(textDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }

    private static class LableDetectionTask extends AsyncTask<Object, Void, String[]> {
        private final WeakReference<SignUpActivity3> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;

        LableDetectionTask(SignUpActivity3 activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        //background 작업
        @Override
        protected String[] doInBackground(Object... params) {

            String[] b = new String[100];

            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                //text로 변경

                String[] info = convertResponseToString(response);
                return info;

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return b;
        }

        //background 작업 후 UI 작업

        protected void onPostExecute(String[] result) {

            SignUpActivity3 activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                progressBar.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                //imageDetail = activity.findViewById(R.id.imgDetail);


                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0:
                            comName.setText(result[0]);
                            break;
                        case 1:
                            comNumber.setText(result[1]);
                            break;
                        case 2:
                            comCEO.setText(result[2]);
                            break;
                        /*
                        case 3:
                            comAddr.setText(result[3]);
                            break;
                        */
                        default:
                            break;
                    }
                }
            }
        }
    }

    //cloudVision call
    private void callCloudVision(final Bitmap bitmap) {
        // Switch text to loading
        //imgDetail.setText("이미지 처리중...");

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String[]> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();

        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    //텍스트 출력
    private static String[] convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "I found these things:\n\n";

        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();
        if (labels != null) {
            message = labels.get(0).getDescription();

        } else {
            message = "nothing";
        }

        String[] messageresult = pickInfo(message);
        return messageresult;
    }

    //문자열 처리
    private static String[] pickInfo(String original) {

        String target[] = {"업 체 명 :", "사업자등록번호 :", "대 표 자 명 :", "주\n"};//,"주\n소 :"
        //String target[] = {"홍길동 ", "123456 ", "유효기한:", "서울특별시\n"};//,"주\n소 :"
        int[] target_num = new int[4];

        String result[] = new String[4];

        for (int i = 0; i < 4; i++) {
            target_num[i] = original.indexOf(target[i]);
        }

        result[0] = original.substring(target_num[0] + 7, target_num[1]);
        result[0] = result[0].replace("null", "");
        Log.d("Log", result[0]);

        result[1] = original.substring(target_num[1] + 9, target_num[2]);
        result[1] = result[1].replace("null", "");
        Log.d("Log", result[1]);

        result[2] = original.substring(target_num[2] + 9, target_num[3]);
        result[2] = result[2].replace("null", "");
        Log.d("Log", result[2]);

        /*
        result[3] += original.substring(target_num[3],target_num[4]);
        Log.d("Log",result[3]);
        */

        return result;
    }
}

//API Key: AIzaSyAGDwXac6q3EBYEVdMmLEKrTV-mhhPYl-s

//장애인등록사실 확인센터
//http://koams.nl.go.kr/dream/handicap/handicapReportDetail.do
//장애인 사실 여부를 오프라인으로 확인하는 과정에서 업무불편 및 서비스 지연이 발생하기 때문에
//행정자치부의 행정정보공동이용망을 통해 정보 전달 후 사실여부 결과를 수신받는다
