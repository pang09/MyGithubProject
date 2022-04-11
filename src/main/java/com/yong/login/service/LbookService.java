package com.yong.login.service;

import com.yong.login.common.lang.Result;
import com.yong.login.entity.Lbook;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yong
 * @since 2022-03-11
 */
public interface LbookService extends IService<Lbook> {

    Result getBooks(Long page, Long limit);

    Result saveBooks(Lbook lbook);

    Result deleteBooks(Integer id);

    Result addBooks(Lbook lbook);

    Result bookInformation(Integer id);
}
