/**
 * 
 */
/**
 * 
 */
module trabajofinalobjetos2 {
	requires org.junit.jupiter.api;
    requires org.mockito; 
    requires org.mockito.junit.jupiter;
    
    requires net.bytebuddy;
    requires net.bytebuddy.agent;
    
	requires jdk.incubator.vector;
	
	opens terminalPortuaria to org.mockito, org.junit.jupiter.api, org.mockito.junit.jupiter;
	
}