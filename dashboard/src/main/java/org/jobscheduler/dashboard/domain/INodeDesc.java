package org.jobscheduler.dashboard.domain;

/**
 * interface for node info pertaining to command execution
 */
public interface INodeDesc extends INodeBase{
    
    /**
     * Returns hostname
     *
     * @return Name of node
     */
    String getHostname();
    String getNodename();

    /**
     * Checks equality with another node description
     *
     * @param n
     * @return
     */
    boolean equals(INodeDesc n);
}
