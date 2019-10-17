package com.example.demojson.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName HelloController
 * @Description
 * @Author wangzhen
 * @Date 2019/10/16 下午6:28
 **/

@RestController
public class HelloController {

    /**
     * 简单的spb的helloword
     * @return
     */
    @RequestMapping("/springboot")
    public String Hello() {
        return "Hello SpringBoot!";
    }

    /**
     * 通过@ResponseBody 方式，需要在@RequestMapping 中，添加produces = "application/json;charset=UTF-8",设定返回值的类型。
     * @param jsonParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/body/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String writeByBody(@RequestBody JSONObject jsonParam) {
        // 直接将json信息打印出来
        System.out.println(jsonParam.toJSONString());

        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("method", "@ResponseBody");
        result.put("data", jsonParam);

        return result.toJSONString();
    }


    /**
     * 通过HttpServletResponse 获取到输出流后，写出数据到客户端，也就是网页了
     * 通过HttpServletResponse 写json到客户端<br/>
     * @param jsonParam
     * @param resp
     */
    @RequestMapping(value = "/resp/data", method = RequestMethod.POST)
    public void writeByResp(@RequestBody JSONObject jsonParam, HttpServletResponse resp) {

        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("method", "HttpServletResponse");
        result.put("data", jsonParam);

        //写json数据到客户端
        this.writeJson(resp, result);
    }

    /**
     * 通过HttpServletResponse 获取到输出流后，写出数据到客户端，也就是网页了
     * 写数据到浏览器上<br/>
     * @param resp
     * @param json
     */
    public void writeJson(HttpServletResponse resp ,JSONObject json ) {
        PrintWriter out = null;
        try {
            //设定类容为json的格式
            resp.setContentType("application/json;charset=UTF-8");
            out = resp.getWriter();
            //写到客户端
            out.write(json.toJSONString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


}
