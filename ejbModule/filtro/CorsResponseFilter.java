package filtro;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;


@Provider
public class CorsResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {

//		requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
//		requestContext.getHeaders().add("Access-Control-Allow-Headers",
//				"Origin, X-Requested-With, Content-Type, Accept");
//		requestContext.getHeaders().add("Access-Control-Allow-Methods",
//				"GET, POST, DELETE, PUT, OPTIONS");

		
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept");
		responseContext.getHeaders().add("Access-Control-Allow-Methods",
				"GET, POST, DELETE, PUT, OPTIONS");

	

	}
}
