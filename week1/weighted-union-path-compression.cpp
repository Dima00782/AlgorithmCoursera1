#include <iostream>
#include <vector>
#include <numeric>

class UnionFind {
public:
    UnionFind(const int n) {
        id = std::vector<int>(n, 0);
        std::iota(id.begin() + 1, id.end(), 1);
        size = std::vector<int>(n, 1);
    }

    void Union(const int first, const int second) {
        auto firstRoot = root(first);
        auto secondRoot = root(second);
        if (firstRoot == second) {
            return;
        }

        if (size[firstRoot] > size[secondRoot]) {
            id[secondRoot] = firstRoot;
            size[firstRoot] += size[secondRoot];
        } else {
            id[firstRoot] = secondRoot;
            size[secondRoot] += size[firstRoot];
        }
    }

    bool Connected(const int first, const int second) {
        return root(first) == root(second);
    }

private:
    std::vector<int> id;
    std::vector<int> size;

private:
    int root(int val) {
        while (val != id[val]) {
            id[val] = id[id[val]];
            val = id[val];
        }
        return val;
    }
};

int main() {
    UnionFind uf(6);

    uf.Union(0, 1);
    uf.Union(1, 2);
    uf.Union(3, 4);
    uf.Union(4, 5);
    uf.Union(4, 1);
    std::cout << uf.Connected(5, 1) << std::endl;

    return 0;
}
