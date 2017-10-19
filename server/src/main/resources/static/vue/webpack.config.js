const path = require('path');

module.exports = {
    entry: './src/main.js',
    output: {
        path: path.resolve(__dirname, './dist'),
        publicPath: '/dist/',
        filename: 'build.js'
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/
            },
            {
                test: /\.(jpg|png)$/,
                loader: 'file',
                include: './resources/img'
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            }
        ]
    },
    devServer: {
        historyApiFallback: true,
        compress: true,
        port: 8001
    }
};