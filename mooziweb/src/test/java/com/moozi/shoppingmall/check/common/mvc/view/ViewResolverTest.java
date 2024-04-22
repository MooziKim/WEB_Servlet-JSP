package com.moozi.shoppingmall.check.common.mvc.view;

import com.moozi.mooziweb.common.mvc.view.ViewResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.junit.platform.commons.util.ReflectionUtils;

//todo#6-5 테스트가 통과하도록 ViewResolver를 구현합니다.
class ViewResolverTest {
    final ViewResolver viewResolver = new ViewResolver();

    @Test
    @DisplayName("prefix,postfix initialize")
    void constructor() throws Exception {
        Try<Object> prefixField = ReflectionUtils.tryToReadFieldValue(ViewResolver.class,"prefix",viewResolver);
        Try<Object> postfixField = ReflectionUtils.tryToReadFieldValue(ViewResolver.class,"postfix",viewResolver);

        String prefix = (String) prefixField.get();
        String postfix = (String) postfixField.get();

        Assertions.assertAll(
                ()->Assertions.assertEquals(ViewResolver.DEFAULT_PREFIX,prefix),
                ()->Assertions.assertEquals(ViewResolver.DEFAULT_POSTFIX, postfix)
        );
    }

    @Test
    void getPath() {

        String expected = "/WEB-INF/views/main/index.jsp";
        Assertions.assertAll(
                ()->Assertions.assertEquals(expected,viewResolver.getPath("main/index")),
                ()->Assertions.assertEquals(expected,viewResolver.getPath("/main/index"))
        );
    }

    @Test
    void isRedirect() {
        Assertions.assertAll(
                ()->Assertions.assertTrue(viewResolver.isRedirect("redirect:/index.do")),
                ()->Assertions.assertTrue(viewResolver.isRedirect("REDIRECT:/login.do")),
                ()->Assertions.assertTrue(viewResolver.isRedirect("ReDIrECT:/login.do")),
                ()->Assertions.assertFalse(viewResolver.isRedirect("/main/index")),
                ()->Assertions.assertFalse(viewResolver.isRedirect("/admin/producnt/list"))
        );
    }

    @Test
    void getRedirectUrl() {
        Assertions.assertAll(
                ()->Assertions.assertEquals("/index.do",viewResolver.getRedirectUrl("redirect:/index.do")),
                ()->Assertions.assertEquals("/login.do",viewResolver.getRedirectUrl("REDIRECT:/login.do")),
                ()->Assertions.assertEquals("/admin/product/list.do", viewResolver.getRedirectUrl("ReDIrECT:/admin/product/list.do"))
        );
    }

    @Test
    void getLayOut() {
        Assertions.assertAll(
            ()->Assertions.assertEquals(ViewResolver.DEFAULT_ADMIN_LAYOUT, viewResolver.getLayOut("/admin/product/list")),
            ()->Assertions.assertEquals(ViewResolver.DEFAULT_SHOP_LAYOUT, viewResolver.getLayOut("/mypage/product/list")),
            ()->Assertions.assertEquals(ViewResolver.DEFAULT_SHOP_LAYOUT, viewResolver.getLayOut("/main/index"))
        );
    }
}