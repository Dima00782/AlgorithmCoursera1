#include <iostream>
#include <vector>
#include <numeric>

class UnionFind {
public:
    UnionFind(const int n) {
        id = std::vector<int>(n, 0);
        std::iota(id.begin() + 1, id.end(), 1);
    }

    void Union(const int first, const int second) {
        auto firstRoot = root(first);
        auto secondRoot = root(second);
        id[firstRoot] = secondRoot;
    }

    bool Connected(const int first, const int second) {
        return root(first) == root(second);
    }

private:
    std::vector<int> id;

private:
    int root(const int val) {
        int root = id[val];
        while (root != id[root]) {
            root = id[root];
        }
        return root;
    }
};

int main() {
    UnionFind uf(10);

    uf.Union(3, 4);
    uf.Union(4, 9);
    uf.Union(8, 3);
    std::cout << uf.Connected(8, 9) << std::endl;

    return 0;
}
