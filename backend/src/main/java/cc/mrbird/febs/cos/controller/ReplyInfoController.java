package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.entity.ReplyInfo;
import cc.mrbird.febs.cos.service.ICommodityInfoService;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import cc.mrbird.febs.cos.service.IReplyInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/reply-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyInfoController {

    private final IReplyInfoService replyInfoService;

    private final IOwnerInfoService userInfoService;

    private final ICommodityInfoService commodityInfoService;

    /**
     * 分页获取消息回复信息
     *
     * @param page      分页对象
     * @param replyInfo 消息回复信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ReplyInfo> page, ReplyInfo replyInfo) {
        return R.ok(replyInfoService.selectReplyPage(page, replyInfo));
    }

    /**
     * 获取具体的帖子回复信息
     *
     * @param commodityId 商品ID
     * @return 结果
     */
    @GetMapping("/list/{id}")
    public R replyListByPostId(@PathVariable(value = "id") Integer commodityId) {
        return R.ok(replyInfoService.replyListByPostId(commodityId));
    }

    /**
     * 获取消息回复信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(replyInfoService.list());
    }

    /**
     * 获取消息回复详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(replyInfoService.getById(id));
    }

    /**
     * 新增消息回复信息
     *
     * @param replyInfo 消息回复信息
     * @return 结果
     */
    @PostMapping
    public R save(ReplyInfo replyInfo) {
        // 获取用户信息
        OwnerInfo user = userInfoService.getOne(Wrappers.<OwnerInfo>lambdaQuery().eq(OwnerInfo::getUserId, replyInfo.getUserId()));
        if (user != null) {
            replyInfo.setUserId(user.getId());
        }
        replyInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(replyInfoService.save(replyInfo));
    }

    /**
     * 修改消息回复信息
     *
     * @param replyInfo 消息回复信息
     * @return 结果
     */
    @PutMapping
    public R edit(ReplyInfo replyInfo) {
        return R.ok(replyInfoService.updateById(replyInfo));
    }

    /**
     * 删除消息回复信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(replyInfoService.removeByIds(ids));
    }
}
