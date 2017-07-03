package megaapi.megaapiclient4java;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.stream.Stream;
import megaapi.megaapiclient4java.Interfaces.IWebClient;

public class WebClient extends IWebClient {

    private final int DefaultResponseTimeout = Timeout.Infinite;
    private final int responseTimeout;
    private final String userAgent;

    public WebClient()
    {
        BufferSize = Options.DefaultBufferSize;
        this.responseTimeout = responseTimeout;
        userAgent = generateUserAgent();
    }
    
    public WebClient(int responseTimeout 
        = DefaultResponseTimeout, string userAgent = null)
    {
        BufferSize = Options.DefaultBufferSize;
        this.responseTimeout = responseTimeout;
        this.userAgent = userAgent ?  ? this.GenerateUserAgent();
    }

    public int BufferSize;

    public String PostRequestJson(URI url, String jsonData) {
        using(MemoryStream jsonStream = new MemoryStream(jsonData.getBytes(Charset.forName("UTF-8")))
        
            )
      {
        return this.PostRequest(url, jsonStream, "application/json");
        }
    }

    public String PostRequestRaw(URI url, Stream dataStream) {
        return this.PostRequest(url, dataStream, "application/octet-stream");
    }

    public Stream GetRequestRaw(URI url) {
        HttpWebRequest request = this.CreateRequest(url);
        request.Method = "GET";

        return request.GetResponse().GetResponseStream();
    }

    private String PostRequest(URI url, Stream dataStream, String contentType) {
        HttpWebRequest request = this.CreateRequest(url);
        request.ContentLength = dataStream.Length;
        request.Method = "POST";
        request.ContentType = contentType;

        using(Stream requestStream = request.GetRequestStream()
        
            )
      {
        dataStream.Position = 0;
            dataStream.CopyTo(requestStream, this.BufferSize);
        }

        using(HttpWebResponse response = (HttpWebResponse) request.GetResponse()
        
            )
      {
        using(Stream responseStream = response.GetResponseStream()
            
                )
        {
          using(StreamReader streamReader = new StreamReader(responseStream, Encoding.UTF8)
                
                    )
          {
            return streamReader.ReadToEnd();
                }
            }
        }
    }

    private HttpWebRequest createRequest(URI url) {
        HttpWebRequest request = (HttpWebRequest) WebRequest.Create(url);
        request.Timeout = this.responseTimeout;
        request.UserAgent = this.userAgent;

        return request;
    }

    private String generateUserAgent() {
        AssemblyName assemblyName = Assembly.GetExecutingAssembly().GetName();
        return String.Format("{0} v{1}", assemblyName.Name, assemblyName.Version.ToString(2));
    }
}
