package org.jobscheduler.dashboard.domain;

/**
 * INodeBase identifies a node by its name.
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 * @version $Revision$
 */
public interface INodeBase {
    /**
     * Return the name of the node
     * @return
     */
    public String getNodename();
}
