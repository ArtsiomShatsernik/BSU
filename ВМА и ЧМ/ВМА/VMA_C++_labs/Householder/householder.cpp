#include <iostream>
#include <vector>
#include <iostream>
#include <fstream>
#include <iomanip>
#include <algorithm>

template <typename T>
std::vector<T> operator + (const std::vector <T>& a, const std::vector <T>& b) {
	std::vector <T> result(a.size());
	for (int i = 0; i < a.size(); i++) {
		result[i] = a[i] + b[i];
	}
	return result;
}

template <typename T>
std::vector<T> operator - (const std::vector <T>& a, const std::vector <T>& b) {
	std::vector <T> result(a.size());
	for (int i = 0; i < a.size(); i++) {
		result[i] = a[i] - b[i];
	}
	return result;
}

template <typename T1, typename T2>
std::vector<T2> operator * (T1 a, std::vector <T2>& b) {
	for (int i = 0; i < b.size(); i++) {
		b[i] = b[i] * a;
	}
	return b;
}

template <typename T1, typename T2>
std::vector<T2> operator * (std::vector <T2>& b, T1 a) {
	for (int i = 0; i < b.size(); i++) {
		b[i] = b[i] * a;
	}
	return b;
}

template <typename T>
std::ostream& operator << (std::ostream& os, const std::vector <T>& a) {
	for (int i = 0; i < a.size(); i++) {
		os << std::setprecision(4) << a[i] << ' ';
	}
	return os << std::endl;
}

template <typename T>
std::vector <std::vector <T>> matrix_product(std::vector <std::vector <T>> a_matrix,
	std::vector <std::vector <T>> b_matrix) {
	int res_rows = a_matrix.size();
	int res_columns = b_matrix[0].size();
	std::vector <std::vector <T>> result(res_rows, std::vector <T>(res_columns));
	if (a_matrix[0].size() == b_matrix.size()) {
		for (int i = 0; i < res_rows; i++) {
			for (int j = 0; j < res_columns; j++) {
				for (int k = 0; k < a_matrix[0].size(); k++) {
					result[i][j] += a_matrix[i][k] * b_matrix[k][j];
				}
			}
		}
	}
	else {
		throw "incorret matrix size";
	}
	return result;
}

template <typename T>
T first_matrix_norm(std::vector <std::vector <T>> matrix) {
	int size = matrix.size();
	T sum_res = 0;
	for (int i = 0; i < size; i++) {
		T sum = 0;
		for (int j = 0; j < matrix[i].size(); j++) {
			sum += abs(matrix[i][j]);
		}
		if (sum > sum_res) {
			sum_res = sum;
		}
	}
	return sum_res;
}

template <typename T>
std::vector <std::vector<T>> transpose(std::vector <std::vector<T>> vec) {
	int size = vec.size();
	for (int i = 0; i < size; i++) {
		for (int j = i + 1; j < size; j++) {
			std::swap(vec[i][j], vec[j][i]);
		}
	}
	return vec;
}
template <typename T>
std::vector <std::vector<T>> transpose(std::vector <T> vec) {
	int size = vec.size();
	std::vector <std::vector<T>> result(size, std::vector <T>(1));
	for (int i = 0; i < size; i++) {
		result[i][0] = vec[i];
	}
	return result;
}

template <typename T>
T scalar_product(std::vector<T>& a, std::vector <T>& b) {
	T result = 0;
	for (int i = 0; i < a.size(); i++) {
		result += a[i] * b[i];
	}
	return result;
}


template <typename T>
std::vector < std::vector <T>> householder_method(int size,
	std::vector <std::vector <T>> a_matrix,
	std::vector <std::vector <T>> b_vector, bool show_info = 0) {
	// Метод отражений
	std::vector <std::vector <T>> x_result(size, std::vector <T>(1));
	// Прямой ход
	for (int i = 0; i < size - 1; i++) {
		// Вычисление w
		std::vector <T> s(size);
		for (int k = i; k < size; k++) {
			s[k] = a_matrix[k][i];
		}
		T alpha = sqrt(scalar_product(s, s));
		auto s_temp = s;
		s_temp[i] -= alpha;
		std::vector <std::vector <T>> w(1, std::vector<T>(size));
		w[0] = s_temp * (1 / sqrt(2 * scalar_product(s, s_temp)));
		// Вычисление V
		std::vector <std::vector <T>> V = matrix_product(transpose(w[0]), w);
		for (int i = 0; i < size; i++) {
			V[i] = V[i] * (-2);
			V[i][i] += 1;
		}
		// Умножение слева на V
		a_matrix = matrix_product(V, a_matrix);
		b_vector = matrix_product(V, b_vector);
	}
	// Обратный ход
	for (int i = size - 1; i >= 0; i--) {
		x_result[i][0] = b_vector[i][0];
		for (int j = size - 1; j > i; j--) {
			x_result[i][0] -= x_result[j][0] * a_matrix[i][j];
		}
		x_result[i][0] /= a_matrix[i][i];
	}
	// Вывод данных в консоль
	if (show_info) {
		std::cout << "A в верхнетреугольном виде:" << std::endl;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				std::cout << std::setw(10) << round(a_matrix[i][j] * 10000) / 10000 << std::setw(10);
			}
			std::cout << '|' << round(b_vector[i][0] * 10000) / 10000 << std::endl;
		}
		std::cout << std::endl << "x = (";
		for (int i = 0; i < size; i++) {
			std::cout << std::setw(8) << round(x_result[i][0] * 10000) / 10000 << std::setw(8);
		}
		std::cout << std::setw(1) << ")" << std::endl;
	}
	return x_result;
}



int main() {
	setlocale(LC_ALL, "Russian");
	// Ввод данных
	int size = 5;
	std::vector <std::vector <long double>> x_result(size, std::vector <long double>(1));
	std::vector <std::vector <long double>> a_matrix(size, std::vector <long double>(size));
	std::vector <std::vector <long double>> b_vector(size, std::vector <long double>(1));
	std::ifstream input("input.txt");
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			input >> a_matrix[i][j];
		}
	}
	for (int i = 0; i < size; i++) {
		input >> b_vector[i][0];
	}
	x_result = householder_method(size, a_matrix, b_vector, 1);

	std::vector <std::vector <long double>> r = matrix_product(a_matrix, x_result) - b_vector;
	std::cout << std::endl << "Невязка r = Ax - b:" << std::endl << "( " << std::setw(5);
	for (int i = 0; i < size; i++) {
		std::cout << std::setw(14) << r[i][0] << std::setw(14);
	}
	std::cout << std::setw(5) << ")" << std::endl;
	std::cout << std::endl << "Норма невязки r = " << first_matrix_norm(r) << std::endl;

	return 0;
}