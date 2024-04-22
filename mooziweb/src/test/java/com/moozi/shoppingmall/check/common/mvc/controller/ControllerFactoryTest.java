package com.moozi.shoppingmall.check.common.mvc.controller;

import com.moozi.mooziweb.common.mvc.controller.BaseController;
import com.moozi.mooziweb.common.mvc.controller.ControllerFactory;
import com.moozi.mooziweb.common.mvc.exception.ControllerNotFoundException;
import com.moozi.mooziweb.controller.index.IndexController;
import com.moozi.mooziweb.controller.auth.LoginController;
import com.moozi.mooziweb.controller.auth.LoginPostController;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.function.Try;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mockito;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

//todo#5-6 테스트 코드가 통과하도록 ControllerFactory를 구현합니다.

@Slf4j
class ControllerFactoryTest {
    static final ControllerFactory controllerFactory = new ControllerFactory();
    static final ServletContext ctx = Mockito.mock(ServletContext.class);
    static Set<Class<?>> classSet;
    static final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @BeforeAll
    static void setUp() {
        classSet = Set.of(
                IndexController.class,
                LoginController.class,
                LoginPostController.class);
        controllerFactory.initialize(classSet ,ctx);
    }

    @Test
    @Order(1)
    void initialize() throws Exception {

        Try<Object> s =  ReflectionUtils.tryToReadFieldValue(ControllerFactory.class,"beanMap",controllerFactory);
        ConcurrentMap<String,Object> beanMap = (ConcurrentMap<String, Object>) s.get();
        Assertions.assertAll(
                ()->Assertions.assertEquals(3,beanMap.size()),
                ()->Assertions.assertInstanceOf(IndexController.class,beanMap.get("GET-/index.do")),
                ()->Assertions.assertInstanceOf(LoginController.class,beanMap.get("GET-/login.do")),
                ()->Assertions.assertInstanceOf(LoginPostController.class,beanMap.get("POST-/loginAction.do"))
        );
    }

    @Test
    @Order(2)
    @DisplayName("getController by HttpServletRequest")
    void getController_by_HttpServletRequest() {
        Mockito.when(request.getServletPath()).thenReturn("/index.do");
        Mockito.when(request.getMethod()).thenReturn("GET");
        BaseController controller = (BaseController) controllerFactory.getController(request);
        Assertions.assertInstanceOf(IndexController.class, controller);
    }

    @Test
    @Order(3)
    @DisplayName("getController by Http Method and  serverPath")
    void getController_by_path_method() {
        BaseController controller = (BaseController) controllerFactory.getController("GET","/index.do");
        Assertions.assertInstanceOf(IndexController.class, controller);
    }

    @Test
    @Order(4)
    @DisplayName("controller not found")
    void getController_not_found(){
        Throwable throwable = Assertions.assertThrows(ControllerNotFoundException.class,()->controllerFactory.getController("GET","/index.jsp") );
        log.debug("error:{}",throwable.getMessage());
    }

    @ParameterizedTest(name = "method:{0}, path:{1}")
    @MethodSource("paramsForGetKey")
    @DisplayName("getKey")
    void getKey(String paramMethod, String paramPath) throws Exception {

        Method method = ControllerFactory.class.getDeclaredMethod("getKey", String.class,String.class );
        method.setAccessible(true);
        String key = (String) method.invoke(controllerFactory,paramMethod,paramPath);
        Assertions.assertEquals(String.format("%s-%s",paramMethod,paramPath),key);
    }


    @ParameterizedTest(name = "key={0}-{1}")
    @MethodSource("paramsForGetKey")
    @DisplayName("getBean")
    void getBean(String paramMethod, String paramPath) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ControllerFactory.class.getDeclaredMethod("getBean", String.class );
        method.setAccessible(true);
        BaseController controller = (BaseController) method.invoke(controllerFactory,String.format("%s-%s",paramMethod,paramPath));
        Assertions.assertNotNull(controller);
    }

    private static Stream<Arguments> paramsForGetKey(){
        return Stream.of(
                Arguments.of("GET", "/index.do"),
                Arguments.of("GET", "/login.do"),
                Arguments.of("POST", "/loginAction.do")
        );
    }

}