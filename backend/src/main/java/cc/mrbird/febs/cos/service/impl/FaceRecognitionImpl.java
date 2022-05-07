package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.service.FaceRecognition;
import com.baidu.aip.face.AipFace;
import com.rnkrsoft.bopomofo4j.Bopomofo4j;
import com.rnkrsoft.bopomofo4j.ToneType;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FaceRecognitionImpl implements FaceRecognition {

    @Value("${baidu.face.app-id}")
    private String appId;
    @Value("${baidu.face.app-key}")
    private String appKey;
    @Value("${baidu.face.secret-key}")
    private String secretKey;

    /**
     * 人脸注册
     * @param img
     * @param name
     * @return
     */
    @Override
    public String registered(String img, String name) {
        // 使用本地模式
        Bopomofo4j.local();
        // 请求参数 Map
        HashMap<String, String> options = new HashMap<>();
        // 用户自资料，长度限制256B
        options.put("user_info", name);
        // 图片质量控制 NONE:不进行控制，LOW:较低的质量要求，NORMAL:一般的质量要求，HIGH:较高的质量要求
        options.put("quality_control", "NORMAL");
        // 活体检测控制 NONE: 不进行控制 LOW:较低的活体要求(高通过率 低攻击拒绝率) NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率) HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
        options.put("liveness_control", "LOW");
        // 操作方式 APPEND: 当user_id在库中已经存在时，对此user_id重复注册时，新注册的图片默认会追加到该user_id下,REPLACE : 当对此user_id重复注册时,则会用新图替换库中该user_id下所有图片
        options.put("action_type", "REPLACE");

        // 图片信息(数据大小应小于10M)
        // 图片类型 BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
        String imageType = "BASE64";
        // 用户id（由数字、字母、下划线组成），长度限制128B
        // Bopomofo4j.pinyin() 转化：文句、音调样式、是否大写、是否首字母大写、中间分隔符号
        String userId = Bopomofo4j.pinyin(name, ToneType.WITHOUT_TONE, false, true, "");
        // 用户组id（由数字、字母、下划线组成），长度限制128B
        String groupId = "group1";

        // 人脸注册
        JSONObject jsonObject = new AipFace(appId, appKey, secretKey).addUser(img, imageType, groupId, userId, options);
        System.out.println(jsonObject.toString());
        // 判断是否注册成功
        return "SUCCESS".equals(jsonObject.get("error_msg")) ?
                "success" :
                jsonObject.get("error_msg").toString();
    }

    /**
     * 人脸搜索
     * @param img
     * @return
     */
    @Override
    public String verification(String img) {
        HashMap<String, String> options = new HashMap<>();
        // 最多处理人脸的数量，最大值10
        options.put("max_face_num", "1");
        // 匹配阈值 设置阈值后，score低于此阈值的用户信息将不会返回 最大100 最小0 默认80 此阈值设置得越高，检索速度将会越快
        options.put("match_threshold", "80");
        // 	图片质量控制 NONE: 不进行控制 LOW:较低的质量要求 NORMAL: 一般的质量要求 HIGH: 较高的质量要求
        options.put("quality_control", "LOW");
        // 活体检测控制 NONE: 不进行控制 LOW:较低的活体要求(高通过率 低攻击拒绝率) NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率) HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
        options.put("liveness_control", "LOW");

        // 图片信息(数据大小应小于10M)
        String imageType = "BASE64";
        // 从指定的group中进行查找 用逗号分隔，上限20个
        String groupIdList = "group1";

        // 人脸搜索
        JSONObject jsonObject = new AipFace(appId, appKey, secretKey).search(img, imageType, groupIdList, options);
        System.out.println(jsonObject);
        // 判断是否搜索成功
        if ("SUCCESS".equals(jsonObject.get("error_msg"))) {
            JSONObject jsonArray = jsonObject.getJSONObject("result").getJSONArray("user_list").getJSONObject(0);
            // 返回用户资料
            return jsonArray.get("user_info").toString();
        }
        return "error";
    }

    /**
     * 人脸检测
     * @param img
     * @return
     */
    @Override
    public String faceDetection(String img) {
        // 请求参数 Map
        HashMap<String, String> options = new HashMap<>();
        // 包括age,beauty,expression,face_shape,gender,glasses,landmark,landmark72，landmark150，race,quality,eye_status,emotion,face_type信息 逗号分隔. 默认只返回face_token、人脸框、概率和旋转角度
        options.put("face_field", "age,beauty,face_shape");
        // 最多处理人脸的数量，最大值10
        options.put("max_face_num", "1");
        // 	人脸的类型 LIVE表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等IDCARD表示身份证芯片照：二代身份证内置芯片中的人像照片 WATERMARK表示带水印证件照：一般为带水印的小图，如公安网小图 CERT表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片 默认LIVE
        options.put("face_type", "LIVE");
        // 活体检测控制 NONE: 不进行控制 LOW:较低的活体要求(高通过率 低攻击拒绝率) NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率) HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
        options.put("liveness_control", "LOW");

        // 图片类型 BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
        String imageType = "BASE64";

        // 人脸检测
        JSONObject jsonObject = new AipFace(appId, appKey, secretKey).detect(img, imageType, options);
        System.out.println(jsonObject);
        return "success";
    }
}
