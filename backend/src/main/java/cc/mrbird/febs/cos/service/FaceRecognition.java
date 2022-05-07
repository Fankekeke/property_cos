package cc.mrbird.febs.cos.service;

public interface FaceRecognition {

    // 人脸注册
    String registered(String img, String name);

    // 人脸搜索
    String verification(String img);

    // 人脸检测
    String faceDetection(String img);
}
