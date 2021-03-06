package SampleJackson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleJackson {
	public static void main(String[] args) {
		SampleJackson sample = new SampleJackson();
        System.out.println(sample.getMessage());

        System.out.println("*****jsonからJavaオブジェクトへのシリアライズ*****");

        // ファイル読み込み
        String jsonListData = new String();
        Path file = Paths.get("src/main/resources/log.json");
        try {
			jsonListData = Files.readString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        List<Invoice> invoiceList = sample.convertToListObject(jsonListData);

        // 日付を出力
        for (Invoice invoice : invoiceList) {
            System.out.println(invoice.getCreateDate());
        }

        System.out.println("*****集計開始*****");

        Map<String,Integer> aggregateResult = sample.summary(invoiceList);

        // 集約して結果を出力
		for (Entry<String, Integer> result : aggregateResult.entrySet()) {
			System.out.println(result.getKey());
			System.out.println(result.getValue());
		}
    }

    public String getMessage() {
        return "Start Project!!";
    }

    // JsonからJavaオブジェクトへの変換
    public Invoice convertToObject(String jsonData) {
    	ObjectMapper mapper = new ObjectMapper();
    	Invoice invoice = new Invoice();
		try {
			// JSONからJavaオブジェクトに変換
			invoice = mapper.readValue(jsonData, Invoice.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return invoice;
    }

    // JsonListからJavaオブジェクトリストへの変換
    public List<Invoice> convertToListObject(String jsonListData) {
    	ObjectMapper mapper = new ObjectMapper();
    	List<Invoice> invoiceList = new ArrayList<Invoice>();
		try {
			// JSONからJavaオブジェクトの配列に変換
			invoiceList = Arrays.asList(mapper.readValue(jsonListData, Invoice[].class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return invoiceList;
    }

    // 集約して日付をkey、EgressTotalをvalueとするようなHashMapを返す
    public Map<String, Integer> summary(List<Invoice> invoiceList){
    	Map<String, Integer> map = new LinkedHashMap<>();
    	for (Invoice invoice : invoiceList) {
    		if (!map.containsKey(invoice.getCreateDate())) {
    			map.put(invoice.getCreateDate(), 0);
    		}
    		map.replace(invoice.getCreateDate(),invoice.getEgressTotal()+map.get(invoice.getCreateDate()));
    	}
    	return map;

    }
}
