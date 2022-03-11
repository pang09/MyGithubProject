package com.yong.login.service;

import com.yong.login.entity.Lbook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yong.login.entity.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yong
 * @since 2022-03-11
 */
public interface LbookService extends IService<Lbook> {

    R getBooks(Long page,Long limit);
}
