package com.xw.cloud.controller;

import com.xw.cloud.bean.DeploymentInfo;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.Yaml;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Call;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Api(tags = "Kubernetes 服务管理", description = "处理和管理 Kubernetes 中的服务")
@CrossOrigin
@Controller
@RequestMapping("service")
public class ServiceController {
    @Value("${k8s.config}")
    private String k8sConfig;

    @Value("${k8s.token}")
    private String k8sToken;

    private static final String KUBERNETES_API_SERVER = "https://192.168.243.143:6443";


    @ApiOperation(value = "获取服务列表", notes = "获取 Kubernetes 默认命名空间中所有服务的列表")
    @CrossOrigin
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getDeploymentList() throws IOException, ApiException {


        ModelAndView modelAndView = new ModelAndView("jsonView");
        String kubeConfigPath = ResourceUtils.getURL(k8sConfig).getPath();
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
        Configuration.setDefaultApiClient(client);

        /*AppsV1Api api = new AppsV1Api();
        Call call = api.listDeploymentForAllNamespacesCall(null, null, null, null, null, null, null, null, 5, null, null);*/
        CoreV1Api api2 = new CoreV1Api();
        /*/////
        System.out.println("service的信息"+service.getMetadata().getName());
        System.out.println("service的信息"+service.getMetadata().getLabels());
        System.out.println("service的信息"+service.getSpec().getPorts().get(0).getNodePort());
        //创建时间
        System.out.println("service的信息"+service.getMetadata().getCreationTimestamp());*/

        Call call2 = api2.listNamespacedServiceCall("default", null,null, null, null, null, null, null, null, null, null, null);
        Response response = call2.execute();
        System.out.print(response);
        if (!response.isSuccessful()) {
            modelAndView.addObject("result", "error!");
            return modelAndView;
        }
        modelAndView.addObject("result", response.body().string());

        return modelAndView;
    }



}
