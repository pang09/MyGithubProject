package com.yong.login.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yong.login.common.lang.Result;
import com.yong.login.entity.Lbook;
import com.yong.login.mapper.LbookMapper;
import com.yong.login.service.LbookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Result getBooks(Long page,Long limit) {
        Page<Lbook> pageParam = new Page<>(page, limit);
        IPage<Lbook> userIPage = lbookMapper.selectPage(pageParam, null);
        long total = pageParam.getTotal();
        long pages = pageParam.getPages();
        List<Lbook> goods = userIPage.getRecords();
        ArrayList<Object> arr = new ArrayList<>();
        arr.add(goods);
        arr.add(total);
        arr.add(pages);
        return Result.succ(arr);
    }

    @Override
    public Result saveBooks(Lbook lbook) {
        int u=lbookMapper.updateById(lbook);
        if (u==1){
            return Result.succ("添加成功");
        }
        return Result.fail("添加失败");
    }

    @Override
    public Result deleteBooks(Integer id) {
        int d=lbookMapper.deleteById(id);
        if (d==1){
            return Result.succ("添加成功");
        }
        return Result.fail("添加失败");
    }

    @Override
    public Result addBooks(Lbook lbook) {
        int a=lbookMapper.insert(lbook);
        if (a==1){
            return Result.succ("添加成功");
        }
        return Result.fail("添加失败");
    }

    @Override
    public Result bookInformation(Integer id) {
        Lbook lbook=lbookMapper.selectById(id);

        return Result.succ(lbook);
    }


}
