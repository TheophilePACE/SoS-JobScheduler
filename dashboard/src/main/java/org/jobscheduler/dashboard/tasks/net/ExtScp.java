package org.jobscheduler.dashboard.tasks.net;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.tools.ant.taskdefs.optional.ssh.SSHUserInfo;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;

import java.io.InputStream;
import java.util.Map;

/**
 * ExtScp is ...
 *
 * @author greg
 * @since 2014-03-20
 */
public class ExtScp extends Scp implements SSHTaskBuilder.SCPInterface {

    private String knownhosts;
    private InputStream sshKeyData;
    private long timeout;
    private Map<String, String> sshConfig;
    

    @Override
    public void setSshConfig(Map<String, String> config) {
        this.sshConfig = config;
    }

    protected Session openSession() throws JSchException {
        return SSHTaskBuilder.openSession(this);
    }

    public String getKnownhosts() {
        return knownhosts;
    }

    public void setKnownhosts(String knownhosts) {
        this.knownhosts = knownhosts;
        super.setKnownhosts(knownhosts);
    }

    public InputStream getSshKeyData() {
        return sshKeyData;
    }

    public void setSshKeyData(InputStream sshKeyData) {
        this.sshKeyData = sshKeyData;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Map<String, String> getSshConfig() {
        return sshConfig;
    }

    @Override
    public String getKeyfile() {
        return getUserInfo().getKeyfile();
    }

    @Override
    public SSHUserInfo getUserInfo() {
        return super.getUserInfo();
    }

}
