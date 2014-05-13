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
package org.jobscheduler.dashboard.tasks.net;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.SSHUserInfo;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;
import org.apache.tools.ant.types.Environment;
import org.jobscheduler.dashboard.domain.INodeEntry;
import org.jobscheduler.dashboard.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * SSHTaskFactory constructs a ExtSSHExec task
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 * @version $Revision$
 */
public class SSHTaskBuilder {
	
	private static final Logger log = LoggerFactory.getLogger(SSHTaskBuilder.class);
	
    private static Map<String, String> DEFAULT_SSH_CONFIG = Collections.unmodifiableMap(new HashMap<String, String>() {{
        //use keyboard-interactive last
        put("PreferredAuthentications", "publickey,password,keyboard-interactive");
        put("MaxAuthTries", "1");
    }});

    public static Map<String, String> getDefaultSshConfig() {
        return DEFAULT_SSH_CONFIG;
    }

    /**
     * Open Jsch session, applies private key configuration, timeout and custom ssh configuration
     * @param base
     * @return
     * @throws JSchException
     */
    public static Session openSession(SSHBaseInterface base) throws JSchException {
        JSch jsch = new JSch();
        if (null != base.getUserInfo().getKeyfile()) {
            jsch.addIdentity(base.getUserInfo().getKeyfile());
        }

        if (null != base.getSshKeyData()) {
            try {
                jsch.addIdentity("sshkey", SSHTaskBuilder.streamBytes(base.getSshKeyData()), null, null);
            } catch (IOException e) {
                throw new JSchException("Failed to ready private ssh key data");
            }
        }

        if (!base.getUserInfo().getTrust() && base.getKnownhosts() != null) {
            log.debug("Using known hosts: " + base.getKnownhosts());
            jsch.setKnownHosts(base.getKnownhosts());
        }

        Session session = jsch.getSession(base.getUserInfo().getName(), base.getHost(), base.getPort());
        session.setTimeout((int) base.getTimeout());
        if (base.getVerbose()) {
            log.info("Set timeout to " + base.getTimeout());
        }

        session.setUserInfo(base.getUserInfo());
        if (base.getVerbose()) {
            log.info("Connecting to " + base.getHost() + ":" + base.getPort());
        }
        SSHTaskBuilder.configureSession(base.getSshConfig(), session);

        session.connect();
        return session;
    }

    public static void configureSession(Map<String, String> config, Session session) {
        Properties newconf = new Properties();
        newconf.putAll(config);
        session.setConfig(newconf);
    }

    public static byte[] streamBytes(InputStream sshKeyData) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        org.jobscheduler.dashboard.util.Streams.copyStream(sshKeyData, out);
        return out.toByteArray();
    }

    /**
     * interface that mimics SSHBase methods called
     */
    public static interface SSHBaseInterface {
        SSHUserInfo getUserInfo();

        void setFailonerror(boolean b);

        void setTrust(boolean b);

        void setProject(Project project);

        void setVerbose(boolean b);

        boolean getVerbose();

        void setHost(String s);

        String getHost();

        void setPort(int portNum);

        int getPort();

        void setUsername(String username);

        public void setTimeout(long sshTimeout);

        long getTimeout();

        void setKeyfile(String sshKeypath);

        String getKeyfile();

        void setSshKeyData(InputStream sshKeyData);

        InputStream getSshKeyData();

        void setPassphrase(String s);

        void setPassword(String password);

        void setKnownhosts(String knownhosts);

        String getKnownhosts();

        void setSshConfig(Map<String, String> config);

        Map<String, String> getSshConfig();

    }

    static interface SSHExecInterface extends SSHBaseInterface {

        void setCommand(String commandString);

        void setTimeout(long sshTimeout);

        void setOutputproperty(String s);
    }

    static interface SCPInterface extends SSHBaseInterface {

        void setLocalFile(String absolutePath);

        void setRemoteTofile(String s);
    }

    private static abstract class SSHBaseImpl implements SSHBaseInterface {
        SSHBaseInterface instance;

        public void setFailonerror(boolean b) {
            instance.setFailonerror(b);
        }

        public void setTrust(boolean b) {
            instance.setTrust(b);
        }

        public void setProject(Project project) {
            instance.setProject(project);
        }

        public void setVerbose(boolean b) {
            instance.setVerbose(b);
        }

        public void setHost(String s) {
            instance.setHost(s);
        }

        public void setPort(int portNum) {
            instance.setPort(portNum);
        }

        public void setUsername(String username) {
            instance.setUsername(username);
        }

        public void setTimeout(long sshTimeout) {
            instance.setTimeout(sshTimeout);
        }

        public void setKeyfile(String sshKeypath) {
            instance.setKeyfile(sshKeypath);
        }

        @Override
        public void setSshKeyData(InputStream sshKeyData) {
            instance.setSshKeyData(sshKeyData);
        }

        public void setPassphrase(String s) {
            instance.setPassphrase(s);
        }

        public void setPassword(String password) {
            instance.setPassword(password);
        }

        private SSHBaseImpl(SSHBaseInterface instance) {
            this.instance = instance;
        }

        public void setKnownhosts(String knownhosts) {
            instance.setKnownhosts(knownhosts);
        }

        @Override
        public SSHUserInfo getUserInfo() {
            return instance.getUserInfo();
        }

        @Override
        public boolean getVerbose() {
            return instance.getVerbose();
        }

        @Override
        public String getHost() {
            return instance.getHost();
        }

        @Override
        public int getPort() {
            return instance.getPort();
        }

        @Override
        public long getTimeout() {
            return instance.getTimeout();
        }

        @Override
        public String getKeyfile() {
            return instance.getKeyfile();
        }

        @Override
        public InputStream getSshKeyData() {
            return instance.getSshKeyData();
        }

        @Override
        public String getKnownhosts() {
            return instance.getKnownhosts();
        }

        @Override
        public void setSshConfig(Map<String, String> config) {
            instance.setSshConfig(config);
        }

        @Override
        public Map<String, String> getSshConfig() {
            return instance.getSshConfig();
        }

    }

    private static final class SSHExecImpl extends SSHBaseImpl implements SSHExecInterface {
        ExtSSHExec instance;

        private SSHExecImpl(ExtSSHExec instance) {
            super(instance);
            this.instance = instance;
        }

        public void setCommand(String commandString) {
            instance.setCommand(commandString);
        }


        public void setOutputproperty(String s) {
            instance.setOutputproperty(s);
        }

        public void addEnv(Environment.Variable env) {
            instance.addEnv(env);
        }

    }

    private static final class SCPImpl extends SSHBaseImpl implements SCPInterface {
        ExtScp instance;

        private SCPImpl(ExtScp instance) {
            super(instance);
            this.instance = instance;
        }

        public void setLocalFile(String absolutePath) {
            instance.setLocalFile(absolutePath);
        }

        public void setRemoteTofile(String s) {
            instance.setRemoteTofile(s);
        }

    }

    /**
     * Build a Task that performs SSH command
     *
     * @param loglevel
     * @param nodeentry   target node
     * @param args        arguments
     * @param project     ant project
     * @param dataContext
     *
     * @return task
     */
    public static ExtSSHExec build(final INodeEntry nodeentry, final String[] args,
            final Project project,
            final Map<String, Map<String, String>> dataContext,
            final SSHConnectionInfo sshConnectionInfo, final int loglevel) throws
            BuilderException {


        final ExtSSHExec extSSHExec = new ExtSSHExec();
        build(extSSHExec, nodeentry, args, project, dataContext, sshConnectionInfo,
                loglevel);
                extSSHExec.setAntLogLevel(loglevel);
        return extSSHExec;

    }

    static void build(final SSHExecInterface sshexecTask,
            final INodeEntry nodeentry,
            final String[] args, final Project project,
            final Map<String, Map<String, String>> dataContext,
            final SSHConnectionInfo sshConnectionInfo, final int loglevel) throws
            BuilderException {

        configureSSHBase(nodeentry, project, sshConnectionInfo, sshexecTask, loglevel);

        //nb: args are already quoted as necessary
        final String commandString = StringUtils.join(args, " ");
        sshexecTask.setCommand(commandString);
        sshexecTask.setTimeout(sshConnectionInfo.getSSHTimeout());

        //DataContextUtils.addEnvVars(sshexecTask, dataContext);
    }

    private static void configureSSHBase(final INodeEntry nodeentry, final Project project,
            final SSHConnectionInfo sshConnectionInfo,
            final SSHBaseInterface sshbase, final double loglevel) throws
            BuilderException {

        sshbase.setFailonerror(true);
        sshbase.setTrust(true); // set this true to avoid  "reject HostKey" errors
        sshbase.setProject(project);
        sshbase.setVerbose(loglevel >= Project.MSG_VERBOSE);
        sshbase.setHost(nodeentry.extractHostname());
        // If the node entry contains a non-default port, configure the connection to use it.
        if (nodeentry.containsPort()) {
            final int portNum;
            try {
                portNum = Integer.parseInt(nodeentry.extractPort());
            } catch (NumberFormatException e) {
                throw new BuilderException("Port number is not valid: " + nodeentry.extractPort(), e);
            }
            sshbase.setPort(portNum);
        }
        final String username = sshConnectionInfo.getUsername();
        if (null == username) {
            throw new BuilderException("username was not set");
        }
        sshbase.setUsername(username);

        final AuthenticationType authenticationType = sshConnectionInfo.getAuthenticationType();
        if (null == authenticationType) {
            throw new BuilderException("SSH authentication type undetermined");
        }
        switch (authenticationType) {
            case privateKey:
                /**
                 * Configure keybased authentication
                 */
                final String sshKeypath = sshConnectionInfo.getPrivateKeyfilePath();
                final String sshKeyResource = sshConnectionInfo.getPrivateKeyResourcePath();
                if (null != sshKeyResource) {
                    if (!PathUtil.asPath(sshKeyResource).getPath().startsWith("ssh-key/")) {
                        throw new BuilderException("SSH Private key path is expected to start with \"ssh-key/\": " +
                                sshKeyResource);
                    }
                    try {
                        InputStream privateKeyResourceData = sshConnectionInfo.getPrivateKeyResourceData();
                        sshbase.setSshKeyData(privateKeyResourceData);
                    } catch (Exception e) {
                        log.error("Failed to read SSH Private key stored at path: " +
                                sshKeyResource+": "+ e);
                        throw new BuilderException("Failed to read SSH Private key stored at path: " +
                                sshKeyResource,e);
                    } 
                } else if (null != sshKeypath && !"".equals(sshKeypath)) {
                    if (!new File(sshKeypath).exists()) {
                        throw new BuilderException("SSH Keyfile does not exist: " + sshKeypath);
                    }
                    project.log("Using ssh keyfile: " + sshKeypath, Project.MSG_DEBUG);
                    sshbase.setKeyfile(sshKeypath);
                } else {
                    throw new BuilderException("SSH Keyfile or storage path must be set to use privateKey " +
                            "authentication");
                }
                final String passphrase = sshConnectionInfo.getPrivateKeyPassphrase();
                if (null != passphrase) {
                    sshbase.setPassphrase(passphrase);
                } else {
                    sshbase.setPassphrase(""); // set empty otherwise password will be required
                }
                break;
            case password:
                final String password = sshConnectionInfo.getPassword();
                final boolean valid = null != password && !"".equals(password);
                if (!valid) {
                    throw new BuilderException("SSH Password was not set");
                }
                sshbase.setPassword(password);
                break;
        }
        Map<String, String> sshConfig = sshConnectionInfo.getSshConfig();
        Map<String, String> baseConfig = new HashMap<String, String>(getDefaultSshConfig());
        if(null!=sshConfig) {
            baseConfig.putAll(sshConfig);
        }
        sshbase.setSshConfig(baseConfig);
    }

    public static Scp buildScp(final INodeEntry nodeentry, final Project project,
            final String remotepath, final File sourceFile,
            final SSHConnectionInfo sshConnectionInfo, final int loglevel) throws
            BuilderException {


        final ExtScp scp = new ExtScp();
        buildScp(scp, nodeentry, project, remotepath, sourceFile, sshConnectionInfo, loglevel);
        return scp;
    }

    static void buildScp(final SCPInterface scp, final INodeEntry nodeentry,
            final Project project, final String remotepath, final File sourceFile,
            final SSHConnectionInfo sshConnectionInfo, final int loglevel) throws
            BuilderException {

        if (null == sourceFile) {
            throw new BuilderException("sourceFile was not set");
        }
        if (null == remotepath) {
            throw new BuilderException("remotePath was not set");
        }
        final String username = sshConnectionInfo.getUsername();
        if (null == username) {
            throw new BuilderException("username was not set");
        }

        configureSSHBase(nodeentry, project, sshConnectionInfo, scp, loglevel);

        //Set the local and remote file paths

        scp.setLocalFile(sourceFile.getAbsolutePath());
        final String sshUriPrefix = username + "@" + nodeentry.extractHostname() + ":";
        scp.setRemoteTofile(sshUriPrefix + remotepath);
    }

    public static class BuilderException extends Exception {
        public BuilderException() {
        }

        public BuilderException(String s) {
            super(s);
        }

        public BuilderException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public BuilderException(Throwable throwable) {
            super(throwable);
        }
    }


    public static enum AuthenticationType {
        privateKey,
        password
    }

    /**
     * Defines the authentication input for a build
     */
    public static interface SSHConnectionInfo {
        public AuthenticationType getAuthenticationType();

        public String getPrivateKeyfilePath();

        public String getPrivateKeyResourcePath();

        public InputStream getPrivateKeyResourceData() throws IOException;

        /**
         * Return the private key passphrase if set, or null.
         */
        public String getPrivateKeyPassphrase();

        public String getPassword();

        public int getSSHTimeout();

        public String getUsername();

        public Map<String,String> getSshConfig();
    }

}
