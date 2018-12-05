//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xs.rongly.framework.stater.jdbc.autoConfig.mybatis;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.io.VFS;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class SpringBootVFS extends VFS {
    private final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());

    public SpringBootVFS() {
    }

    private static String preserveSubpackageName(URI uri, String rootPath) {
        String uriStr = uri.toString();
        int start = uriStr.indexOf(rootPath);
        return uriStr.substring(start);
    }

    public boolean isValid() {
        return true;
    }

    protected List<String> list(URL url, String path) throws IOException {
        Resource[] resources = this.resourceResolver.getResources("classpath*:" + path + "/**/*.class");
        List<String> resourcePaths = new ArrayList();
        Resource[] arr$ = resources;
        int len$ = resources.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Resource resource = arr$[i$];
            resourcePaths.add(preserveSubpackageName(resource.getURI(), path));
        }

        return resourcePaths;
    }
}
