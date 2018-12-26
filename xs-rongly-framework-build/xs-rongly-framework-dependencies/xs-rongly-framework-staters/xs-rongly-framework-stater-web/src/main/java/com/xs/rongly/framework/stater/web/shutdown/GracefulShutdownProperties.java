//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xs.rongly.framework.stater.web.shutdown;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "hn-shutdown.graceful"
)
@Data
public class GracefulShutdownProperties {
    private int wait = 30;
    private int timeout = 30;

}
