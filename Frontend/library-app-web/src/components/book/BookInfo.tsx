import { useState } from "react";
import { Book } from "../../models/book/Book";
import { Box, Modal } from "@mui/material";
import { Button, Card, CardBody } from "@material-tailwind/react";

export interface BookInfoProps {
  book: Book;
  availableCopiesInBuildingCount: number;
}

export default function BookInfo(props: BookInfoProps) {
  const { book, availableCopiesInBuildingCount } = props;
  const [showDescriptionModal, setShowDescriptionModal] =
    useState<boolean>(false);

  return (
    <>
      <Modal
        open={showDescriptionModal}
        onClose={() => setShowDescriptionModal(false)}
        className="flex justify-center items-center"
      >
        <Box sx={{ width: "60%" }}>
          <Card>
            <CardBody>
              <h2 className="font-bold">Description:</h2>
              <p>{book.description}</p>
            </CardBody>
          </Card>
        </Box>
      </Modal>
      <div>
        <div className="flex justify-center items-center w-full font-bold text-lg">
          {book.title}
        </div>
        <div>Publisher: {book.publisher.name}</div>
        <div>
          <Button
            size="sm"
            color="white"
            onClick={() => setShowDescriptionModal(true)}
          >
            See description
          </Button>
        </div>
        <div>Pages number: {book.pagesNumber}</div>
        <div>
          Available copies in the building: {availableCopiesInBuildingCount}
        </div>
      </div>
    </>
  );
}
