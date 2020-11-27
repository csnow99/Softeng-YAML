package edu.wpi.cs.yaml.demo.http;

import edu.wpi.cs.yaml.demo.model.Vote;

public class GetVotesResponse extends GenericResponse{
    public Vote vote;

    //when code 200
    public GetVotesResponse(String response, Vote v) {
        super(response);
        this.vote = v;
    }

    //when code not 200 null is returned as the choice
    public GetVotesResponse(int code, String response) {
        super(response, code);
        this.vote = null;
    }
}
