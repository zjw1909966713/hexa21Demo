package com.highrock.config;


import com.highrock.Hexa21Application;
import com.highrock.controller.IndexController;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeSerialization;

/***
 * @decription:
 * @author: Jony Z
 * @date: 2025-03-20 15:24
 * @version: 1.0
 */
public class LambdaRegistrationFeature implements Feature {

    @Override
    public void duringSetup(DuringSetupAccess access) {
        // TODO 这里需要将lambda表达式所使用的成员类都注册上来,具体情况视项目情况而定,一般扫描@Controller和@Service的会多点.
        RuntimeSerialization.registerLambdaCapturingClass(Hexa21Application.class);
        RuntimeSerialization.registerLambdaCapturingClass(IndexController.class);
    }

}
