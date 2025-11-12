package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.ReplyInfoMapper;
import cc.mrbird.febs.cos.entity.ReplyInfo;
import cc.mrbird.febs.cos.service.IReplyInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class ReplyInfoServiceImpl extends ServiceImpl<ReplyInfoMapper, ReplyInfo> implements IReplyInfoService {

    /**
     * 分页获取消息回复信息
     *
     * @param page      分页对象
     * @param replyInfo 消息回复信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectReplyPage(Page<ReplyInfo> page, ReplyInfo replyInfo) {
        return baseMapper.selectReplyPage(page, replyInfo);
    }

    /**
     * 获取具体的帖子回复信息
     *
     * @param commodityId 商品ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> replyListByPostId(Integer commodityId) {
        return baseMapper.replyListByPostId(commodityId);
    }
}
