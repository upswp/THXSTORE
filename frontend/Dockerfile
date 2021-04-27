FROM node:lts-alpine as build-stage
WORKDIR /homepage

COPY . .
RUN npm install
RUN npm run build

FROM nginx:stable-alpine as production-stage

RUN rm /etc/nginx/conf.d/default.conf
COPY ./homepage.conf /etc/nginx/conf.d/homepage.conf
COPY --from=build-stage ./homepage/dist /usr/share/nginx/html/homepage
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]