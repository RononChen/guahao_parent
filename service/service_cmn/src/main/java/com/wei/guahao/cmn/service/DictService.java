package com.wei.guahao.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface DictService extends IService<Dict> {
//    根据数据id查询子数据列表
    List<Dict> selectChildData(long id);

//    导出数据字典
    void exportDate(HttpServletResponse response);
}
