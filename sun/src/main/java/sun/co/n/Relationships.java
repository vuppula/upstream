package sun.co.n;

import java.util.Map;

import org.restexpress.RestExpress;
import org.restexpress.common.exception.ConfigurationException;
import sun.co.n.compoundid.SampleCompoundIdentifierEntity;
import sun.co.n.uuid.SampleUuidEntity;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;

public abstract class Relationships
{
	private static Map<String, String> ROUTES;

	public static void define(RestExpress server)
	{
		ROUTES = server.getRouteUrlsByName();

		HyperExpress.relationships()
		.forClass(SampleUuidEntity.class)
			.rel(RelTypes.SELF, href(Constants.Routes.SINGLE_UUID_SAMPLE))
			.rel(RelTypes.UP, href(Constants.Routes.SAMPLE_UUID_COLLECTION))

		.forCollectionOf(SampleCompoundIdentifierEntity.class)
			.rel(RelTypes.SELF, href(Constants.Routes.SAMPLE_COMPOUND_COLLECTION))
				.withQuery("limit={limit}")
				.withQuery("offset={offset}")
			.rel(RelTypes.NEXT, href(Constants.Routes.SAMPLE_COMPOUND_COLLECTION) + "?offset={nextOffset}")
				.withQuery("limit={limit}")
				.optional()
			.rel(RelTypes.PREV, href(Constants.Routes.SAMPLE_COMPOUND_COLLECTION) + "?offset={prevOffset}")
				.withQuery("limit={limit}")
				.optional()

		.forClass(SampleCompoundIdentifierEntity.class)
			.rel(RelTypes.SELF, href(Constants.Routes.SINGLE_COMPOUND_SAMPLE))
			.rel(RelTypes.UP, href(Constants.Routes.SAMPLE_COMPOUND_COLLECTION));
	}

	private static String href(String name)
	{
		String href = ROUTES.get(name);
		if (href == null) throw new ConfigurationException("Route name not found: " + name);
		return href;
	}

}
