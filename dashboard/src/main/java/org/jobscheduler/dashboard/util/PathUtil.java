/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.jobscheduler.dashboard.util;


/**
 * Utility methods for paths
 */
public class PathUtil {

    public static final Path ROOT = asPath("/");

    public static class PathImpl implements Path {
        String pathString;
        private String name;

        public PathImpl(String pathString) {
            this.pathString = pathString;
            name = pathName(pathString);
        }

        @Override
        public String getPath() {
            return pathString;
        }

        @Override
        public String toString() {
            return pathString;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PathImpl)) return false;

            PathImpl path = (PathImpl) o;

            if (!pathString.equals(path.pathString)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return pathString.hashCode();
        }
    }

    public static Path asPath(String path) {
        if (null == path) {
            return null;
        }
        return new PathImpl(cleanPath(path));
    }

    /**
     * Return true if the given path starts with the given root
     *
     * @param path
     * @param root
     *
     * @return
     */
    public static boolean hasRoot(Path path, Path root) {
        return hasRoot(path.getPath(), root.getPath());
    }

    /**
     * Return true if the given path starts with the given root
     *
     * @param path
     * @param root
     *
     * @return
     */
    public static boolean hasRoot(String path, String root) {
        String p = cleanPath(path);
        String r = cleanPath(root);
        return p.equals(r) || p.startsWith(r + "/");
    }

    public static Path parentPath(Path path) {
        return asPath(parentPathString(path.getPath()));
    }

    /**
     * Return true if the path is the root
     *
     * @param path
     *
     * @return
     */
    public static boolean isRoot(String path) {
        return isRoot(asPath(path));
    }
    /**
     * Return true if the path is the root
     * @param path
     * @return
     */
    public static boolean isRoot(Path path) {
        return path.equals(ROOT);
    }

    /**
     * Return the string representing the parent of the given path
     * @param path path string
     * @return parent path string
     */
    public static String parentPathString(String path) {
        String[] split = cleanPath(path).split("/");
        if (split.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                if (i > 0) {
                    stringBuilder.append("/");
                }
                stringBuilder.append(split[i]);
            }
            return stringBuilder.toString();
        }
        return "";
    }

    public static String cleanPath(String path) {
        if (path.endsWith("/")) {
            path = path.replaceAll("/+$", "");
        }
        if (path.startsWith("/")) {
            path = path.replaceAll("^/+", "");
        }
        return path.replaceAll("/+", "/");
    }

    public static String pathName(String path) {
        String[] split = cleanPath(path).split("/");
        if (split.length > 0) {
            return split[split.length - 1];
        }
        return null;
    }

    public static String removePrefix(String rootPath, String extpath) {
        if (!hasRoot(extpath, rootPath)) {
            return extpath;
        }
        return cleanPath(cleanPath(extpath).substring(cleanPath(rootPath).length()));
    }

    /**
     * Append one path to another
     *
     * @param prefix  prefix
     * @param subpath sub path
     *
     * @return sub path appended to the prefix
     */
    public static Path appendPath(Path prefix, String subpath) {
        return asPath(appendPath(prefix.getPath(), subpath));
    }

    /**
     * Append one path to another
     *
     * @param prefixPath prefix
     * @param subpath    sub path
     *
     * @return sub path appended to the prefix
     */
    public static String appendPath(String prefixPath, String subpath) {
        return cleanPath(prefixPath) + "/" + cleanPath(subpath);
    }









}
