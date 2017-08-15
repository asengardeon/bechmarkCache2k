package cache2kPOC;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

public class Main {
	
	private static final int trezentosMil = 300000;
	private static final int cemMil = 30000000;
	
	private void runCompare() {
		List<String> searches = new ArrayList<>();
		Map<String, String> mapTest = new HashMap<>();
		
		Cache2kBuilder<String, String> cacheBuilder = new Cache2kBuilder<String, String>(){};
        cacheBuilder.eternal(true);
        cacheBuilder.keepDataAfterExpired(true);
        cacheBuilder.entryCapacity(Integer.MAX_VALUE);
        Cache<String, String> cache = cacheBuilder.build();
			
		String keyConst = "KeyName";
		String valueConst = "Val";
		
		
		String k;
		String v;
		int num;
		Map<String, String> list = getSeeds(keyConst, valueConst);
		mapTest.putAll(list);
		cache.putAll(list);
		
		searches = createSearchLists(keyConst);
		
		searchInMap(searches, mapTest);
		
		searchInCache2k(searches, cache);		
		
	}

	private void searchInCache2k(List<String> searches, Cache<String, String> cache) {
		String k;
		String v;
		System.out.println("Pesquisando no cache2k as "+Instant.now());		
		for (Iterator iterator = searches.iterator(); iterator.hasNext();) {
			k = (String) iterator.next();
			v = cache.get(k);
		}		
		System.out.println("Finalizou as "+Instant.now());
		System.out.println("Cache counter: "+cache.asMap().size());
	}

	private void searchInMap(List<String> searches, Map<String, String> mapTest) {
		String k;
		String v;
		System.out.println("Pesquisando no map as "+Instant.now());
		for (Iterator iterator = searches.iterator(); iterator.hasNext();) {
			k = (String) iterator.next();
			v = mapTest.get(k);
		}
		System.out.println("Finalizou as "+Instant.now());
		System.out.println("MAp counter: "+mapTest.size());
	}

	private List<String> createSearchLists(String keyConst) {
		Random r = new Random();
		System.out.println("criando lista de pesquisas  as "+Instant.now());
		List<String> result = new ArrayList<>();
		for (int i = 0; i < cemMil; i++) {			
			result.add(keyConst+r.nextInt(cemMil));
		} 
		System.out.println("Finalizou as "+Instant.now());
		return result;
	}

	private Map<String, String> getSeeds(String keyConst, String valueConst) {
		String k;
		String v;
		int num;
		Random r = new Random();
		Map<String, String> result = new HashMap<>();
		System.out.println("iniciando sementes as "+Instant.now());
		for (int i = 0; i < trezentosMil; i++) {
			num = r.nextInt(trezentosMil);
			k = keyConst+num;
			v = valueConst+num;
			result.put(k, v);			
		}
		System.out.println("Finalizou as "+Instant.now());
		return result;
	};
	
	public static void main(String[] args) {
		Main main = new Main();		
		main.runCompare();
	} 

}
