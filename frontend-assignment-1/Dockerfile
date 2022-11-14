FROM node:18-alpine

WORKDIR /app

COPY . ./


RUN yarn global add typescript
RUN yarn install
RUN yarn build
RUN npm install -g serve

ENV NODE_ENV=production
EXPOSE 3000

CMD ["yarn", "serve"]