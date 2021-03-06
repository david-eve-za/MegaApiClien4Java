package megaapi.megaapiclient4java.JsonSerialization;

public abstract class RequestBase {

    public RequestBase(String action) {
        this.action = action;
        this.queryArguments = new NameValueCollection();
    }

    @JsonProperty("a")
    private final String action;

    public String getAction() {
        return action;
    }

    @JsonIgnore
    private final NameValueCollection queryArguments;

    public NameValueCollection getQueryArguments() {
        return queryArguments;
    }
    
    public NameValueCollection setQueryArguments(String name, NameValue queryArgument) {
        this.queryArguments[name] = queryArgument;
    }
}
