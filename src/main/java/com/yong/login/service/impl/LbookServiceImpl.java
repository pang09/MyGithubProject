package com.yong.login.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yong.login.entity.Lbook;
import com.yong.login.entity.R;
import com.yong.login.mapper.LbookMapper;
import com.yong.login.service.LbookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yong
 * @since 2022-03-11
 */
@Service
public class LbookServiceImpl extends ServiceImpl<LbookMapper, Lbook> implements LbookService {

    @Resource
    private LbookMapper lbookMapper;

    @Override
    public R getBooks(Long page,Long limit) {
        Page<Lbook> pageParam = new Page<>(page, limit);
        IPage<Lbook> userIPage = lbookMapper.selectPage(pageParam, null);
        long total = pageParam.getTotal();
        long pages = pageParam.getPages();
        List<Lbook> goods = userIPage.getRecords();
        return R.ok().message("查询成功").data("data",goods).data("total",total).data("pages",pages);
    }
}
