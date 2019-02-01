# AIlease

Object Classification on Android phone and show information about disability public and job.


You can Classification in convinece and normal situation.


## Steps

1. clone and extract the repository.
2. download the .pb file from Tensorflow or pretrained yourself.
3. move optimized.pb file to the following path
4. jang\app\src\main\assets\
5. build and run the app.





tf로 만들기

1. 학습을 시킨다. - pb 추출
python scripts/retrain.py --bottleneck_dir=tf_files/bottlenecks/ --how_many_training_steps=5000 --model_dir=tf_files/models/mobilenet_v1_0.50_224/ --summaries_dir=tf_files/training_summaries/mobilenet_0.50_224 --output_graph=tf_files/shop_graph_final.pb --output_labels=tf_files/shop_labels_final.txt --image_dir=tf_files/datasets_store/

2. 라벨과 잘 맞는지 추론
python scripts/label_image.py --graph=tf_files/shop_app.tflite --labels=tf_files/shop_labels_final.txt --image=tf_files/Lotteria_3.png --input_layer=Mul

3. 그래프 경량화 진행
python scripts/optimize.py --input=./tf_files/shop_graph_final.pb --output=./tf_files/opt_shop_app.pb/ --input_names="Mul" --output_names="final_result"

4. tf로 변환
toco  --output_file=./tf_files/tflite_shop_graph.tflite   --graph_def_file=./tf_files/opt_shop_app.pb  --output_format=TFLITE  --input_shapes=1,299,299,3  --input_arrays=Mul --output_array=final_result  --inference_type=FLOAT
