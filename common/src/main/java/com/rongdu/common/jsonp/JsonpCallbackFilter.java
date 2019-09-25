
package com.rongdu.common.jsonp;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.exception.BizException;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.StringUtils;

/**
 * 
 * 通过过滤器统一处理跨域请求
 * 跨域请求统一规范：1、要提交callback请求参数；2、需在请求URL后添加.jsonp后缀
 *  http(s)://xxxx/xxx.jsonp
 * @author sunda
 * @version 2017-03-20
 */
public class JsonpCallbackFilter implements Filter {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        String callback = request.getParameter("callback");
        if(StringUtils.isNotBlank(callback)) {
            OutputStream out = response.getOutputStream();
            GenericResponseWrapper wrapper = new GenericResponseWrapper(response);
            chain.doFilter(request, wrapper);            
            out.write(new String(callback + "(").getBytes());
            //Handle error case. If the callback is used, the Exception Handling is skipped
            if(wrapper.getData() == null || wrapper.getData().length == 0){
            	throw new BizException(ErrInfo.SERVER_ERROR);
            }else{
            	out.write(wrapper.getData());            	
            }
            out.write(new String(");").getBytes());
            wrapper.setContentType("text/javascript;charset=UTF-8");
            wrapper.setHeader("Access-Control-Allow-Origin", "*");
            wrapper.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
            out.close();
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {}
}