package algorithms;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.*;


/**
 * class to compose optimum subtopologies
 */
public class OPTAlgorithm{

    private MyGraph<MyVertex, MyEdge> graph;
    private List<List<Integer>> requestList;
    private List<MyVertex> dcList;
    private List<MyVertex> terminalNodeList;
    public double optValue;

    public OPTAlgorithm(MyGraph<MyVertex, MyEdge> graph, List<List<Integer>> requestList){
        this.graph = graph;
        this.requestList = requestList;
        optValue = inf;
    }

    /**
     * @return optValue
     */
    public double solve() {
        terminalNodeList = new ArrayList<MyVertex>();
        dcList = new ArrayList<MyVertex>();
        /** prepare lists for terminal nodes and data centers */
        for(MyVertex v: graph.getVertices()){
            if(v.getVertexID() == "terminal"){
                terminalNodeList.add(v);
            }
            else if (v.getVertexID() == "data center"){
                dcList.add(v);
            }
        }

        mySubtopologyList = new ArrayList<List<MyVertex>>();
        for (int rk = 0; rk < requestList.size(); rk++) {
            mySubtopologyList.add(new ArrayList<MyVertex>());
        }

        init();
        dfs(0);
        System.gc();
        return optValue / requestList.size();
    }

    private List<List<MyVertex>> mySubtopologyList;
    private void dfs(int rk) {
        if (rk == requestList.size()) {
            dfs2( 0, 0);
            return;
        }
        // r_kのターミナルノードを選ぶ
        for (MyVertex terminal : terminalNodeList) {
            mySubtopologyList.get(rk).add(terminal);
            dfs( rk + 1);
            mySubtopologyList.get(rk).remove(mySubtopologyList.get(rk).size() - 1);
        }
    }
    private void dfs2(int n,int rk) {
        if (rk == requestList.size()) {
            if (capacityCheck()) {
                int value = evaluateSubtopologyList();
//                System.err.println(mySubtopologyList);
//                System.err.println(value);
                optValue = Math.min(optValue, value);
            }
            return;
        }
        if (n == requestList.get(rk).size()) {
            dfs2( 0, rk + 1);
            return;
        }
        // rk が使うDCを重複なしで選ぶ
        for (MyVertex dc : dcList) {
            if (mySubtopologyList.get(rk).contains(dc)) continue;
            mySubtopologyList.get(rk).add(dc);
            dfs2( n+1, rk);
            mySubtopologyList.get(rk).remove(mySubtopologyList.get(rk).size() - 1);
        }
    }
    private boolean capacityCheck() {
        HashMap<MyVertex,Integer> capMap = new HashMap<MyVertex, Integer>();
        for (MyVertex dc : dcList) {
            capMap.put(dc, dc.getCapacity());
        }
        for (int i = 0; i < mySubtopologyList.size(); i++) {
            List<MyVertex> mySubtopology = mySubtopologyList.get(i);
            for (int j = 0; j < mySubtopology.size(); j++) {
                MyVertex v = mySubtopology.get(j);
                if (v.getVertexID() == "data center"){
                    if (!v.getVNFList().contains(requestList.get(i).get(j - 1))) return false;
                    Integer cap = capMap.get(v);
                    if (cap == 0) return false;
                    capMap.put(v, cap - 1);
                }
            }
        }
        return true;
    }

    int[][] g;
    int[][] d;
    HashMap<MyVertex, Integer> vToi;
    int inf = Integer.MAX_VALUE / 2 - 1;
    private void init() {
        int n = graph.getVertexCount();
        d = new int[n][n];
        vToi = new HashMap<MyVertex, Integer>();
        // initialize matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                d[i][j] = inf;
                if(i == j)d[i][j] = 0;
            }
        }
        // Vertex to Integer
        int vcount = 0;
        for (MyVertex v : graph.getVertices()) {
            vToi.put( v, vcount++);
        }
        // Graph to Matrix
        for (MyVertex v : graph.getVertices()) {
            for (MyVertex neighor : graph.getNeighbors(v)) {
                int v1 = vToi.get(v);
                int v2 = vToi.get(neighor);
                d[v1][v2] = 1;
                d[v2][v1] = 1;
            }
        }

        // shortest path
        for (int k = 0; k < n; ++k)
            for (int i = 0; i < n; ++i)
                for (int j = 0; j < n; ++j)
                    d[i][j] = Math.min( d[i][j], d[i][k] + d[k][j] );
    }
    private Integer evaluateSubtopologyList() {
        int ans = 0;
        for (List<MyVertex> mySubtopology : mySubtopologyList) {
            int[] T = new int[mySubtopology.size()];
            List<Integer> TList = new ArrayList<Integer>();
            for (MyVertex v : mySubtopology) {
                TList.add(vToi.get(v));
            }
            Collections.sort(TList);
            for (int i=0;i<T.length;i++) {
                T[i] = TList.get(i);
            }
            ans += minimumSteinerTree(T);
        }
        return ans;
    }
    private Integer minimumSteinerTree(int[] T) {
        int n = graph.getVertexCount();
        int K = T.length, INF = 1<<29;
        if(K<=1)return 0;
        int[][] dp = new int[1<<K][n];
        for(int[]d:dp)Arrays.fill(d, INF);
        for(int p=0;p<K;p++)for(int q=0;q<n;q++)dp[1<<p][q]=d[T[p]][q];

        for(int S=1;S<1<<K;S++){
            if((S&(S-1))==0)continue;
            for(int p=0;p<n;p++)for(int sub=0;sub<S;sub++){
                if((S|sub)==S){
                    dp[S][p] = Math.min(dp[S][p], dp[sub][p]+dp[S-sub][p]);
                }
            }
            for(int p=0;p<n;p++)for(int q=0;q<n;q++){
                dp[S][p] = Math.min(dp[S][p], dp[S][q]+d[q][p]);
            }
        }
        int res = INF;
        for(int S=0;S<1<<K;S++)for(int q=0;q<n;q++){
            res = Math.min(res, dp[S][q] + dp[((1<<K)-1)-S][q]);
        }
        return res;
    }
}

