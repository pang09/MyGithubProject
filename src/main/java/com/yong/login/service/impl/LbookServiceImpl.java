package com.yong.login.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yong.login.entity.Lbook;
import com.yong.login.mapper.LbookMapper;
import com.yong.login.service.LbookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public R saveBooks(Lbook lbook) {
        int u=lbookMapper.updateById(lbook);
        if (u==1){
            return R.ok();
        }
        return R.error();
    }

    @Override
    public R deleteBooks(Integer id) {
        int d=lbookMapper.deleteById(id);
        if (d==1){
            return R.ok();
        }
        return R.error();
    }

    @Override
    public R addBooks(Lbook lbook) {
        int a=lbookMapper.insert(lbook);
        if (a==1){
            return R.ok();
        }
        return R.error();
    }

    @Override
    public R bookInformation(Integer id) {
        Lbook lbook=lbookMapper.selectById(id);

        return R.ok().data("data",lbook);
    }


}
