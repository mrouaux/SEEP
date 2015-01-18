/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.imperial.lsds.seep.infrastructure.monitor.slave;

import uk.ac.imperial.lsds.seep.GLOBALS;
import uk.ac.imperial.lsds.seep.infrastructure.monitor.Factory;

/**
 *
 * @author mrouaux
 */
public class MonitorSlaveFactory implements Factory<MonitorSlave> {

    private int operatorId;
    
    private String masterHost;
    private int masterPort;
    
    private int freqSeconds;
    
    /**
     * Convenience constructor.
     * @param operatorId 
     */
    public MonitorSlaveFactory(int operatorId) {
        this.operatorId = operatorId;
        
        this.masterHost = GLOBALS.valueFor("mainAddr");
        this.masterPort = Integer.valueOf(GLOBALS.valueFor("monitorManagerPort"));
        
        this.freqSeconds = Integer.valueOf(GLOBALS.valueFor("monitorInterval"));
    }
    
    @Override
    public MonitorSlave create() {
        return new MonitorSlave(operatorId, masterHost, masterPort, freqSeconds);
    }
    
    @Override
    public MonitorSlave create(Object...args) {
        // Use host address from arguments to factory method
        if (args.length > 0) {
            this.masterHost = ((args[0] instanceof String) && (args[0] != null))?
                    (String)args[0] : GLOBALS.valueFor("mainAddr");
        }
        
        return create();
    }
}
