package org.example.merger;

import java.util.*;
import java.util.stream.Collectors;

public class HashJoiner {
    public static long searchForJoin=0;
    public static long startTime=0;
    public static long endTime=0;

    public static long joinCNT=0;
    // 使用哈希连接两个表，基于多个 joinColumns 进行连接
    public static Table hashJoin(Table table1, Table table2, List<String> parentJoinColumns,boolean needNewIndex) {

        if(table1.getRowCount() * table2.getRowCount()==0){
            return new Table(0);
        }

        Table smallerTable = table1.getRows().getSize() <= table2.getRows().getSize() ? table1 : table2;
        Table largerTable = table1.getRows().getSize() > table2.getRows().getSize() ? table1 : table2;

        startTime = System.currentTimeMillis();

//        Table resultTable=JoinTwoTableNaive(smallerTable,largerTable,parentJoinColumns,needNewIndex);
        Table resultTable=JoinTwoTableQuick(smallerTable,largerTable,parentJoinColumns,needNewIndex);

        endTime = System.currentTimeMillis();
        searchForJoin += (endTime - startTime);
        return resultTable;
    }

    public static Table JoinTwoTableQuick(Table smallerTable,Table largerTable,List<String> parentJoinColumns,boolean needNewIndex){
        long capacity= smallerTable.getCapacity()>largerTable.getCapacity()?smallerTable.getCapacity():largerTable.getCapacity();
        // 存储连接后的结果
        Table resultTable = new Table(capacity);

        Map<String, List<Row>> smallHashIndex = smallerTable.getHashIndex();
        Map<String, List<Row>> largeHashIndex = largerTable.getHashIndex();
        Set <String > sharedIndex=new HashSet<>(smallHashIndex.keySet());
        sharedIndex.retainAll(largeHashIndex.keySet());


        for (  String indexKey:sharedIndex){
            for (Row largeRow: largeHashIndex.get(indexKey)){
                for(Row smallRow: smallHashIndex.get(indexKey)){
                    resultTable.addRow( largeRow.join(smallRow,parentJoinColumns, needNewIndex));
                    joinCNT++;
                }
            }
        }
        return resultTable;
    }



}
