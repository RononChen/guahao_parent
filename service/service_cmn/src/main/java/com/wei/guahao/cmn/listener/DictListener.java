package com.wei.guahao.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.wei.guahao.cmn.mapper.DictMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class DictListener extends AnalysisEventListener<DictEeVo> {
    private DictMapper dictMapper;
    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }
    //    一行一行的读取数据
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
//        读到数据之后，我们需要将数据写入到数据库中 这个时候需要在这个类用到DictMapper
//        通过构造方法将DictMapper传递进来
//        将dictEeVo类型转换成Dict类型
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
