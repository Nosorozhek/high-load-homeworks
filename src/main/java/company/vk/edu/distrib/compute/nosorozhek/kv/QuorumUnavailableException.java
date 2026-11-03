package company.vk.edu.distrib.compute.nosorozhek.kv;

import java.io.IOException;

public class QuorumUnavailableException extends IOException {
    public QuorumUnavailableException() {
        super("Not enough nodes available");
    }
}
