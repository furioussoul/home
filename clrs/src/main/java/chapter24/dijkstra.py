# 初始节点s
class Vertex:
    def __init__(self, vid, out_list):
        self.vid = vid  # d点
        self.outList = out_list  # 邻接表
        self.dist = float('inf')  # s到该节点的最短距离
        self.prev = 0  # 上个顶点的id
        self.know = False

    def _lt_(self, other):
        return self.dist < other.dist


# 顶点1可达顶点2，4
v1 = Vertex(1, [2, 4])
v1.dist = 0
v2 = Vertex(2, [4, 5])
v3 = Vertex(3, [1, 6])
v4 = Vertex(4, [3, 5, 6, 7])
v5 = Vertex(5, [7])
v6 = Vertex(6, [])
v7 = Vertex(7, [6])

edges = dict()


# 存储边的权值（距离）,权值必须大于0
def add_edge(from_, to_, value):
    edges[(from_, to_)] = value


# 顶点1到2的距离是2个单位
add_edge(1, 2, 2)
add_edge(1, 4, 1)
add_edge(3, 1, 4)
add_edge(4, 3, 2)
add_edge(2, 4, 3)
add_edge(2, 5, 10)
add_edge(4, 5, 2)
add_edge(3, 6, 5)
add_edge(4, 6, 8)
add_edge(4, 7, 4)
add_edge(7, 6, 1)
add_edge(5, 7, 6)

vList = [False, v1, v2, v3, v4, v5, v6, v7]
vSet = {v1, v2, v3, v4, v5, v6, v7}


# 找到vList中dist最小的元素。
# 从vSet中删除它，并返回它。
# set中有三种元素：返回值、比返回值的dist大的inf和有限值。
# 显然从dist比v.dist大的顶点到v的距离不可能比v.dist还要小。
# inf是未连接到的顶点，把它们看成一个inf块，显然不可能有某条路径到达inf块后又回到v的dist比v.dist小。
# 所以v.dist一定是从s到v的最优路径距离。
def get_min_dist_v():
    min_ = -1
    index = 0
    for j in range(1, len(vList)):
        # vList初始化时v1的dist最小
        if j == 1 and vList[j].know is False:
            index = 1
            break
        elif vList[j].know is True:
            continue
        elif min_ == -1 or vList[j].dist < min_:
            min_ = vList[j].dist
            index = j

    vSet.remove(vList[index])
    vList[index].know = True
    return vList[index]


if __name__ == '__main__':

    while len(vSet) != 0:
        # 遍历每个顶点
        v = get_min_dist_v()
        # 计算v可达节点的dist
        for w in v.outList:
            if vList[w].know is False and v.dist + edges[v.vid, w] < vList[w].dist:
                vList[w].dist = v.dist + edges[v.vid, w]
                vList[w].prev = v.vid

    for i in range(1, len(vList)):
        print('v{0}.prev:{1}  v{0}.dist:{2}'.format(i, vList[i].prev, vList[i].dist))
