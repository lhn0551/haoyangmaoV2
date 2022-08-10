package com.h.haoyangmaov2;

import com.hjq.toast.ToastUtils;


import java.io.IOException;
import java.lang.reflect.Type;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.TypeParser;
import rxhttp.wrapper.utils.Converter;

@Parser(name = "Response", wrappers = {PageList.class})
public class ResponseParser<T> extends TypeParser<T> {

    //该构造方法是必须的
    protected ResponseParser() {
        super();
    }

    //如果依赖了RxJava，该构造方法也是必须的
    public ResponseParser(Type type) {
        super(type);
    }

    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        //将okhttp3.Response转换为自定义的Response对象
        Response<T> data = Converter.convertTo(response, Response.class, types);  //这里的types就是自定义Response<T>里面的泛型类型
//        Logs.e("code====" + data.getCode());
        T t = data.getData(); //获取data字段
        if (t == null) {
            /*
             * 考虑到有些时候服务端会返回：{"errorCode":0,"errorMsg":"关注成功"}  类似没有data的数据
             * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
             * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
             */
            t = (T) data.getMsg();
        }

        if ((data.getCode() != 0 && data.getCode() != 200) || t == null) {//这里假设code不等于200，代表数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getCode()), data.getMsg(), response);
        }
        return t;
    }
}
