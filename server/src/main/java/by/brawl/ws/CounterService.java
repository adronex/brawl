package by.brawl.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CounterService {

    private AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private CounterHandler counterHandler;

   // @Scheduled(fixedDelay = 1000)
    public void sendCounterUpdate() {
        //counterHandler.counterIncrementedCallback(counter.incrementAndGet());
    }

}