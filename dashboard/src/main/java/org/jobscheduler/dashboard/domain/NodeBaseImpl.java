package org.jobscheduler.dashboard.domain;

/**
 * Implementation of INodeBase
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 * @version $Revision$
 */
public class NodeBaseImpl implements INodeBase {
    String nodename;

    NodeBaseImpl(){
        
    }
    NodeBaseImpl(final String nodename) {
        setNodename(nodename);
    }

    public String getNodename() {
        return nodename;
    }

    /**
     * Set the node name
     * @param nodename name of the node
     */
    public void setNodename(final String nodename) {
        this.nodename = nodename;
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof INodeBase)) {
            return false;
        }

        final INodeBase base = (INodeBase) o;

        if (nodename != null ? !nodename.equals(base.getNodename()) : base.getNodename() != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return (nodename != null ? nodename.hashCode() : 0);
    }

    /**
     * Create a NodeBaseImpl
     * @param nodename the node name
     * @return NodeBaseImpl
     */
    public static NodeBaseImpl create(final String nodename) {
        return new NodeBaseImpl(nodename);
    }
}
