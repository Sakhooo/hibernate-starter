package kz.example;

import kz.entity.Company;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

  @Test
  void proxyDynamic() {
    Company company = new Company();
    Proxy proxy = (Proxy) Proxy.newProxyInstance(
            company.getClass().getClassLoader(),
            company.getClass().getInterfaces(),
            (proxy1, method, args) -> method.invoke(proxy1, args));
  }
}
