package com.wei.guahao.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface DictService extends IService<Dict> {
//    根据数据id查询子数据列表
    List<Dict> selectChildData(long id);

//    导出数据字典
    void exportDate(HttpServletResponse response);

//    导入数据字典
    void importData(MultipartFile file);

//    查询医院等级 或者地址
    String getName(String dictCode, Long value);

//    根据dictCode获取下级节点
    List<Dict> findByDictCode(String dictCode);
}
