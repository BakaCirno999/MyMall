package com.mymall.mall.config.handler;

import com.mymall.mall.common.Constants;
import com.mymall.mall.common.MallException;
import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.config.annotation.TokenToMallUser;
import com.mymall.mall.entity.User;
import com.mymall.mall.entity.UserToken;
import com.mymall.mall.mapper.UserMapper;
import com.mymall.mall.mapper.UserTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenToMallUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTokenMapper userTokenMapper;

    public TokenToMallUserMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToMallUser.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getParameterAnnotation(TokenToMallUser.class) instanceof TokenToMallUser) {
            User user = null;
            String token = webRequest.getHeader("token");
            if (null != token && !"".equals(token) && token.length() == Constants.TOKEN_LENGTH) {
                UserToken userToken = userTokenMapper.selectByToken(token);
                if (userToken == null || userToken.getExpireTime().getTime() <= System.currentTimeMillis()) {
                    MallException.fail(ServiceResultEnum.TOKEN_EXPIRE_ERROR.getResult());
                }
                user = userMapper.selectById(userToken.getUserId());
                if (user == null) {
                    MallException.fail(ServiceResultEnum.USER_NULL_ERROR.getResult());
                }
                if (user.getLockedFlag().intValue() == 1) {
                    MallException.fail(ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult());
                }
                return user;
            } else {
                MallException.fail(ServiceResultEnum.NOT_LOGIN_ERROR.getResult());
            }
        }
        return null;
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

}
