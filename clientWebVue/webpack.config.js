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
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.png/,
                loader: "file-loader"
            }
        ]
    },
    devServer: {
        historyApiFallback: true,
        compress: true,
        port: 8001
    },
    resolve: {
        alias: {
            images: path.resolve(__dirname, 'resources/img/'),
        }
    }
};