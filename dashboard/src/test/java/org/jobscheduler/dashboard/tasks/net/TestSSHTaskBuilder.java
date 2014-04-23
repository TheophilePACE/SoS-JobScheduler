package org.jobscheduler.dashboard.tasks.net;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.SSHUserInfo;
import org.apache.tools.ant.types.Environment;
import org.jobscheduler.dashboard.domain.NodeEntryImpl;
import org.jobscheduler.dashboard.util.CLIUtils;
import org.jobscheduler.dashboard.util.PathUtil;

/**
 * TestSSHTaskBuilder is ...
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class TestSSHTaskBuilder extends TestCase {


    static class testSSHBaseInterface implements SSHTaskBuilder.SSHBaseInterface {
        boolean failonerror;
        boolean trust;
        Project project;
        private boolean verbose;
        private String host;
        private int port;
        private long timeout;
        private SSHUserInfo userInfo;
        String username;
        private String keyfile;
        String passphrase;
        String password;
        private String knownhosts;
        private Map<String,String> sshConfig;
        private InputStream sshKeyData;


        public void setFailonerror(boolean failonerror) {
            this.failonerror = failonerror;
        }

        public void setTrust(boolean trust) {
            this.trust = trust;
        }

        public void setProject(Project project) {
            this.project = project;
        }

        public void setVerbose(boolean verbose) {
            this.verbose = verbose;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setKeyfile(String keyfile) {
            this.keyfile = keyfile;
        }

        @Override
        public void setSshKeyData(InputStream sshKeyData) {
            this.sshKeyData=sshKeyData;
        }


        public void setPassphrase(String passphrase) {
            this.passphrase = passphrase;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setKnownhosts(String knownhosts) {
            this.knownhosts = knownhosts;
        }

        public boolean getVerbose() {
            return verbose;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getKeyfile() {
            return keyfile;
        }

        public String getKnownhosts() {
            return knownhosts;
        }

        public Map<String, String> getSshConfig() {
            return sshConfig;
        }

        public void setSshConfig(Map<String, String> sshConfig) {
            this.sshConfig = sshConfig;
        }

        public InputStream getSshKeyData() {
            return sshKeyData;
        }

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public SSHUserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(SSHUserInfo userInfo) {
            this.userInfo = userInfo;
        }
    }

    static class testSCPInterface extends testSSHBaseInterface implements SSHTaskBuilder.SCPInterface {
        String localFile;
        String remoteTofile;

        public void setLocalFile(String localFile) {
            this.localFile = localFile;
        }

        public void setRemoteTofile(String remoteTofile) {
            this.remoteTofile = remoteTofile;
        }
    }

    static class testSSHExecInterface extends testSSHBaseInterface implements SSHTaskBuilder.SSHExecInterface {
        String command;
        long timeout;
        String outputproperty;
        boolean failonerror;

        ArrayList<Environment.Variable> environment = new ArrayList<Environment.Variable>();


        public void setCommand(String command) {
            this.command = command;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public void setOutputproperty(String outputproperty) {
            this.outputproperty = outputproperty;
        }

        public void addEnv(Environment.Variable env) {
            environment.add(env);
        }
    }

    private static class testSSHConnectionInfo implements SSHTaskBuilder.SSHConnectionInfo {
        SSHTaskBuilder.AuthenticationType authenticationType;
        String privateKeyfilePath;
        String privateKeyResourcePath;
        String password;
        int SSHTimeout;
        String username;
        String privateKeyPassphrase;
        InputStream privateKeyResourceData;
        IOException privateKeyResourceDataIOException;
        Map<String,String> sshConfig;

        public SSHTaskBuilder.AuthenticationType getAuthenticationType() {
            return authenticationType;
        }

        public String getPrivateKeyfilePath() {
            return privateKeyfilePath;
        }

        public String getPassword() {
            return password;
        }

        public int getSSHTimeout() {
            return SSHTimeout;
        }

        public String getUsername() {
            return username;
        }

        public String getPrivateKeyPassphrase() {
            return privateKeyPassphrase;
        }

        public String getPrivateKeyResourcePath() {
            return privateKeyResourcePath;
        }

        public InputStream getPrivateKeyResourceData() throws IOException {
            if (null != privateKeyResourceDataIOException) {
                throw privateKeyResourceDataIOException;
            }
            return privateKeyResourceData;
        }

        public Map<String, String> getSshConfig() {
            return sshConfig;
        }
    }

    File testKeyfile;
    File testSourcefile;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testKeyfile = File.createTempFile("test", "keyfile");
        testSourcefile = File.createTempFile("test", "sourcefile");

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        testKeyfile.delete();
        testSourcefile.delete();
    }

    class testState {
        final NodeEntryImpl node = new NodeEntryImpl("hostname", "nodename");
        String[] strings = {"a", "b", "c"};
        final Project project = new Project();
        final Map<String, Map<String, String>> stringMapMap = new HashMap<String, Map<String, String>>();
        final testSSHConnectionInfo sshConnectionInfo = new testSSHConnectionInfo();
        int loglevel = 0;
        String remotePath;
        File sourceFile;

        testState() {
            sshConnectionInfo.authenticationType = SSHTaskBuilder.AuthenticationType.privateKey;
            sshConnectionInfo.privateKeyfilePath = testKeyfile.getAbsolutePath();
            sshConnectionInfo.username = "testusername";
        }
    }

    private void assertInvariable(testState state, testSSHExecInterface test) {
        //never changes
        assertEquals(null, test.outputproperty);
        assertInvariableBase(state, test);
    }

    private void assertInvariableBase(testState state, testSSHBaseInterface test) {
        assertNull(test.getKnownhosts());
        assertEquals(true, test.failonerror);
        assertEquals(state.project, test.project);
        assertEquals(true, test.trust);
    }

    private void runBuildSSH(final testState state, final testSSHExecInterface test) throws
        SSHTaskBuilder.BuilderException {
        SSHTaskBuilder.build(test, state.node, state.strings, state.project, state.stringMapMap,
            state.sshConnectionInfo, state.loglevel);
    }

    private void runBuildSCP(final testState state, final testSCPInterface test) throws
        SSHTaskBuilder.BuilderException {
        SSHTaskBuilder.buildScp(test, state.node, state.project, state.remotePath,
            state.sourceFile, state.sshConnectionInfo, state.loglevel);
    }

    public void testBuildSSHDefault() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        runBuildSSH(state, test);


        assertEquals(CLIUtils.generateArgline(null, state.strings), test.command);
        assertEquals("hostname", test.getHost());
        assertEquals(0, test.getPort());
        assertEquals(testKeyfile.getAbsolutePath(), test.getKeyfile());
        assertEquals("", test.passphrase);
        assertEquals(null, test.password);
        assertEquals(0, test.timeout);
        assertEquals("testusername", test.username);
        assertEquals(false, test.getVerbose());
        assertInvariable(state, test);
        assertNotNull(test.getSshConfig());
        assertEquals("1", test.getSshConfig().get("MaxAuthTries"));
        assertEquals("publickey,password,keyboard-interactive", test.getSshConfig().get("PreferredAuthentications"));

    }

    public void testBuildSSHVariables() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        state.node.setHostname("hostname:33");
        state.sshConnectionInfo.SSHTimeout = 600;
        state.sshConnectionInfo.username = "usernameValue";

        runBuildSSH(state, test);

        assertEquals(CLIUtils.generateArgline(null, state.strings), test.command);
        assertEquals("hostname", test.getHost());
        assertEquals(33, test.getPort());
        assertEquals(testKeyfile.getAbsolutePath(), test.getKeyfile());
        assertEquals("", test.passphrase);
        assertEquals(null, test.password);
        assertEquals(600, test.timeout);
        assertEquals("usernameValue", test.username);
        assertEquals(false, test.getVerbose());

        //never changes
        assertInvariable(state, test);
    }
    public void testBuildSSHKeyPassphrase() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        state.node.setHostname("hostname:33");
        state.sshConnectionInfo.SSHTimeout = 600;
        state.sshConnectionInfo.username = "usernameValue";
        state.sshConnectionInfo.privateKeyPassphrase = "passphraseValue";

        runBuildSSH(state, test);

        assertEquals(CLIUtils.generateArgline(null, state.strings), test.command);
        assertEquals("hostname", test.getHost());
        assertEquals(33, test.getPort());
        assertEquals(testKeyfile.getAbsolutePath(), test.getKeyfile());
        assertEquals("passphraseValue", test.passphrase);
        assertEquals(null, test.password);
        assertEquals(600, test.timeout);
        assertEquals("usernameValue", test.username);
        assertEquals(false, test.getVerbose());

        //never changes
        assertInvariable(state, test);
    }

    public void testBuildSSHKeyNoUsername() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        state.sshConnectionInfo.authenticationType = SSHTaskBuilder.AuthenticationType.privateKey;
        state.sshConnectionInfo.username = null;

        //null node hostname
        try {
            runBuildSSH(state, test);
            fail("Shouldn't succeed");
        } catch (SSHTaskBuilder.BuilderException e) {
            assertEquals("username was not set", e.getMessage());
        }

    }

    public void testBuildSSHPassword() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        state.sshConnectionInfo.authenticationType = SSHTaskBuilder.AuthenticationType.password;
        state.sshConnectionInfo.password = "passwordValue";
        state.sshConnectionInfo.username = "usernameValue";

        runBuildSSH(state, test);

        assertEquals(CLIUtils.generateArgline(null, state.strings), test.command);
        assertEquals("hostname", test.getHost());
        assertEquals(0, test.getPort());
        assertEquals(null, test.getKeyfile());
        assertEquals(null, test.passphrase);
        assertEquals("passwordValue", test.password);
        assertEquals(0, test.timeout);
        assertEquals("usernameValue", test.username);
        assertEquals(false, test.getVerbose());

        //never changes
        assertInvariable(state, test);
    }

    public void testBuildSSHPasswordNoUsername() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        state.sshConnectionInfo.authenticationType = SSHTaskBuilder.AuthenticationType.password;
        state.sshConnectionInfo.password = "passwordValue";
        state.sshConnectionInfo.username = null;

        //null node hostname
        try {
            runBuildSSH(state, test);
            fail("Shouldn't succeed");
        } catch (SSHTaskBuilder.BuilderException e) {
            assertEquals("username was not set", e.getMessage());
        }

    }

    public void testBuildSSHKeyResource() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        state.sshConnectionInfo.authenticationType = SSHTaskBuilder.AuthenticationType.privateKey;
        state.sshConnectionInfo.privateKeyResourcePath = "/ssh-key/key1.pem";
        state.sshConnectionInfo.privateKeyResourceData = new ByteArrayInputStream("data".getBytes());
        state.sshConnectionInfo.username = "usernameValue";

        runBuildSSH(state, test);

        assertEquals(CLIUtils.generateArgline(null, state.strings), test.command);
        assertEquals("hostname", test.getHost());
        assertEquals(0, test.getPort());
        assertNotNull(test.getSshKeyData());
        assertEquals(null, test.getKeyfile());
        assertEquals("", test.passphrase);
        assertEquals(null, test.password);
        assertEquals(0, test.timeout);
        assertEquals("usernameValue", test.username);
        assertEquals(false, test.getVerbose());

        //never changes
        assertInvariable(state, test);
    }
    public void testBuildSSHKeyResource_ioexception() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        state.sshConnectionInfo.authenticationType = SSHTaskBuilder.AuthenticationType.privateKey;
        state.sshConnectionInfo.privateKeyResourcePath = "/ssh-key/key1.pem";
        state.sshConnectionInfo.privateKeyResourceData = new ByteArrayInputStream("data".getBytes());
        state.sshConnectionInfo.privateKeyResourceDataIOException = new IOException("blah");
        state.sshConnectionInfo.username = "usernameValue";

        try {
            runBuildSSH(state, test);
            fail("expected exception");
        } catch (SSHTaskBuilder.BuilderException e) {
            assertEquals(IOException.class, e.getCause().getClass());
        }

    }

    public void testBuildSSHVerbose() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();


        state.loglevel = 3;

        runBuildSSH(state, test);

        assertEquals(true, test.getVerbose());

        assertEquals(CLIUtils.generateArgline(null, state.strings), test.command);
        assertEquals("hostname", test.getHost());
        assertEquals(0, test.getPort());
        assertEquals(testKeyfile.getAbsolutePath(), test.getKeyfile());
        assertEquals("", test.passphrase);
        assertEquals(null, test.password);
        assertEquals(0, test.timeout);
        assertEquals("testusername", test.username);

        //never changes
        assertInvariable(state, test);
    }

    public void testBuildSSHConfigSimple() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();
        state.sshConnectionInfo.sshConfig=new HashMap<String, String>(){{
            put("abc", "123");
        }};

        runBuildSSH(state, test);

        assertNotNull(test.getSshConfig());
        assertEquals(3,test.getSshConfig().size());
        assertEquals("123", test.getSshConfig().get("abc"));
        assertEquals("1", test.getSshConfig().get("MaxAuthTries"));
        assertEquals("publickey,password,keyboard-interactive", test.getSshConfig().get("PreferredAuthentications"));
    }
    public void testBuildSSHConfigOverrideDefaults() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();
        state.sshConnectionInfo.sshConfig = new HashMap<String, String>() {{
            put("MaxAuthTries", "2");
        }};

        runBuildSSH(state, test);

        assertNotNull(test.getSshConfig());
        assertEquals(2,test.getSshConfig().size());
        assertEquals("2", test.getSshConfig().get("MaxAuthTries"));
        assertEquals("publickey,password,keyboard-interactive", test.getSshConfig().get("PreferredAuthentications"));
    }
    public void testBuildSSHConfigOverrideDefaultsPreferredAuthentications() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();
        state.sshConnectionInfo.sshConfig = new HashMap<String, String>() {{
            put("PreferredAuthentications", "publickey,password");
        }};


        runBuildSSH(state, test);

        assertNotNull(test.getSshConfig());
        assertEquals(2,test.getSshConfig().size());
        assertEquals("1", test.getSshConfig().get("MaxAuthTries"));
        assertEquals("publickey,password", test.getSshConfig().get("PreferredAuthentications"));
    }
    public void testBuildSSHNoHostname() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        /**
         * Set hostname=null
         */
        state.node.setHostname(null);

        //null node hostname
        try {
            runBuildSSH(state, test);
            fail("Shouldn't succeed");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }


    public void testBuildSSHNoAuthtype() throws Exception {
        final testState state = new testState();
        final testSSHExecInterface test = new testSSHExecInterface();

        /**
         * Set authtype=null
         */
        state.sshConnectionInfo.authenticationType = null;

        //no auth type
        try {
            runBuildSSH(state, test);
            fail("Shouldn't succeed");
        } catch (SSHTaskBuilder.BuilderException e) {
            assertNotNull(e);
        }
    }

    public void testBuildSCPDefault() throws Exception {

        final testState state = new testState();
        final testSCPInterface test = new testSCPInterface();

        state.sourceFile = testSourcefile;
        state.remotePath = "/test/path";

        runBuildSCP(state, test);

        assertEquals(testSourcefile.getAbsolutePath(), test.localFile);
        assertEquals("testusername@hostname:/test/path", test.remoteTofile);

        assertEquals("hostname", test.getHost());
        assertEquals(0, test.getPort());
        assertEquals(testKeyfile.getAbsolutePath(), test.getKeyfile());
        assertEquals("", test.passphrase);
        assertEquals(null, test.password);
        assertEquals("testusername", test.username);
        assertEquals(false, test.getVerbose());
        assertInvariableBase(state, test);
    }

    public void testBuildSCPPassword() throws Exception {

        final testState state = new testState();
        final testSCPInterface test = new testSCPInterface();

        state.sshConnectionInfo.authenticationType = SSHTaskBuilder.AuthenticationType.password;
        state.sshConnectionInfo.password = "passwordValue";
        state.sourceFile = testSourcefile;
        state.remotePath = "/test/path";

        runBuildSCP(state, test);

        assertEquals(testSourcefile.getAbsolutePath(), test.localFile);
        assertEquals("testusername@hostname:/test/path", test.remoteTofile);

        assertEquals("hostname", test.getHost());
        assertEquals(0, test.getPort());
        assertEquals(null, test.getKeyfile());
        assertEquals(null, test.passphrase);
        assertEquals("passwordValue", test.password);
        assertEquals("testusername", test.username);
        assertEquals(false, test.getVerbose());
        assertInvariableBase(state, test);
    }

    public void testBuildSCPKeyNoUsername() throws Exception {

        final testState state = new testState();
        final testSCPInterface test = new testSCPInterface();

        state.sourceFile = testSourcefile;
        state.remotePath = "/test/path";

        state.sshConnectionInfo.username = null;

        //null username
        try {
            runBuildSCP(state, test);
            fail("Shouldn't succeed");
        } catch (SSHTaskBuilder.BuilderException e) {
            assertEquals("username was not set", e.getMessage());
        }
    }

    public void testBuildSCPKeyPasswordUsername() throws Exception {

        final testState state = new testState();
        final testSCPInterface test = new testSCPInterface();


        state.sshConnectionInfo.authenticationType = SSHTaskBuilder.AuthenticationType.password;
        state.sshConnectionInfo.password = "passwordValue";
        state.sourceFile = testSourcefile;
        state.remotePath = "/test/path";

        state.sshConnectionInfo.username = null;

        //null username
        try {
            runBuildSCP(state, test);
            fail("Shouldn't succeed");
        } catch (SSHTaskBuilder.BuilderException e) {
            assertEquals("username was not set", e.getMessage());
        }
    }

    public void testBuildSCPNoSourcefile() throws Exception {

        final testState state = new testState();
        final testSCPInterface test = new testSCPInterface();

        state.sourceFile = null;
        state.remotePath = "/test/path";

        //null sourceFile
        try {
            runBuildSCP(state, test);
            fail("Shouldn't succeed");
        } catch (SSHTaskBuilder.BuilderException e) {
            assertEquals("sourceFile was not set", e.getMessage());
        }

    }

    public void testBuildSCPNoRemotePath() throws Exception {

        final testState state = new testState();
        final testSCPInterface test = new testSCPInterface();

        state.sourceFile = testSourcefile;
        state.remotePath = null;

        //null remotePath
        try {
            runBuildSCP(state, test);
            fail("Shouldn't succeed");
        } catch (SSHTaskBuilder.BuilderException e) {
            assertEquals("remotePath was not set", e.getMessage());
        }

    }
}
