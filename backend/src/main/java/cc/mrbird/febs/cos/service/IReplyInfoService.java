package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ReplyInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IReplyInfoService extends IService<ReplyInfo> {

    /**
     * 分页获取消息回复信息
     *
     * @param page      分页对象
     * @param replyInfo 消息回复信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectReplyPage(Page<ReplyInfo> page, ReplyInfo replyInfo);

    /**
     * 获取具体的帖子回复信息
     *
     * @param commodityId 商品ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> replyListByPostId(Integer commodityId);
}
