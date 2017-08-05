package it.gabrieletondi.telldontaskkata.useCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SellItemsRequest {
    private List<SellItemRequest> requests;

    public SellItemsRequest() {
        requests = new ArrayList<>();
    }

    public SellItemsRequest(List<SellItemRequest> requests)
    {
        this.requests = requests;
    }

    public void addItemRequest(SellItemRequest request){
        requests.add(request);
    }

    public List<SellItemRequest> getRequests() {
        return Collections.unmodifiableList(requests);
    }
}
